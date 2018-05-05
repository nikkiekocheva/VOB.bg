package bg.VOB.controller;

import static org.mockito.Mockito.doThrow;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jcodec.api.JCodecException;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.databind.ObjectMapper;

import bg.VOB.model.User;
import bg.VOB.model.Video;
import bg.VOB.model.dao.UserDao;
import bg.VOB.model.dao.VideoDao;
import util.exceptions.InvalidUserDataException;
import util.validation.ObjectToJSON;
import util.validation.SendMail;
import util.validation.Validator;
import util.validation.VideoFrameExtracter;

@Controller
public class UserController {

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String showHomePage(HttpSession session) {
		if(session.getAttribute("user") != null) {
			return "main";	
		}
		return "index";
	}

	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String showMainPage(HttpSession session) {
		if(session.getAttribute("user") == null) {
			return "index";	
		}
		return "main";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginUser(HttpServletRequest req) throws Exception {
		// get the user name and password
		String username = req.getParameter("username");
		String password = req.getParameter("password");

		// check for if the input values are in the data base
		try {
			User user = UserManager.getInstance().signIn(username, password);
			if (user != null) {
				// make a session
				HttpSession session = req.getSession();
				session.setAttribute("user", user);
			}
			return "main";
		} catch (InvalidUserDataException e) {
			return "InvalidLogin";
		}
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String showRegisterUserPage() {
		return "register";
	}

	@RequestMapping(value = "/registerverify", method = RequestMethod.POST)
	public String verifyRegister(HttpServletRequest request, Model model) throws Exception {
		//Get the input data
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String phone = request.getParameter("phoneNumber");
		int age = 0;
		try {
			age = Integer.parseInt(request.getParameter("age"));
		}catch (Exception e) {
			throw new Exception("Age must be a number!!");
		}
		
		
		int code = Validator.generateRegisterCode();
		User user = new User(username, password , email, phone, age);
		
		HttpSession session = request.getSession();
		session.setAttribute("user", user);
		session.setAttribute("code", code);
		
		//Send the verify e-mail
		SendMail.sendMailForRegister(email, code);
		
		return "verifyRegister";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerUser(HttpServletRequest request, HttpSession session) throws Exception {
		int inputCode = Integer.parseInt(request.getParameter("inputCode"));
		int trueCode = (Integer) session.getAttribute("code");
		
		if(inputCode != trueCode) {
			return "verifyRegister";
		}
		
		User user = (User) session.getAttribute("user");
		//Put the new data in to the date base
		UserManager.getInstance().registration(user);
		
		session.invalidate();
		return "index";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public String userLogout(HttpSession session) {
		// Invalidate the session
		session.invalidate();
		// Return to the index page
		return "index";
	}

	@RequestMapping(value = "/profile/{username}", method = RequestMethod.GET)
	public String showUserProfile(@PathVariable("username") String username, Model model, HttpSession session) throws Exception {
		User profileUser = UserDao.getInstance().generateUser(username);
		ArrayList<Video> userVideos = VideoDao.getInstance().getAllVideosByUser(profileUser);

		User sessionUser = (User) session.getAttribute("user");
		boolean isUserFollowed = false;
		if(profileUser != null) {
			if (!sessionUser.getUsername().equals(profileUser.getUsername())) {
				isUserFollowed = UserDao.getInstance().checkIfUserIsFollowingAnotherUser(sessionUser, profileUser);
			}
		}
		model.addAttribute("isUserFolloed", isUserFollowed);
		model.addAttribute("userVideos", userVideos);
		model.addAttribute("user", profileUser);
		return "profile";
	}

	@RequestMapping(value = "/profile", method = RequestMethod.POST)
	public String showUpdateUserProfile() {
		return "updateProfile";
	}

	@RequestMapping(value = "/updateprofile", method = RequestMethod.POST)
	public String updateUserProfile(HttpServletRequest request, HttpSession session) throws Exception {
		User user = (User) session.getAttribute("user");
		//Get the input parameters of the user
		String email = request.getParameter("email");
		String phoneNumber = request.getParameter("phone");
		String newPassword = request.getParameter("newpassword");
		String newPassword1 = request.getParameter("newpassword1");
		String currentPassword = request.getParameter("currentpassword");
		
		//check if the password enterd matches the user password
		if (user.getPassword() != null && BCrypt.checkpw(currentPassword, user.getPassword())) {
			//check if there is password input data
			if (!(newPassword.isEmpty() && newPassword1.isEmpty())) {
				if (!newPassword.equals(newPassword1)) {
					request.setAttribute("error", "The new passwords dont match!!! ");
					return "error";
				}
				user.setPassword(newPassword);
			}
			//check if there is e-mail input data
			if (!email.isEmpty()) {
				user.setEmail(email);
			}
			//check if there is phone number input data
			if (!phoneNumber.isEmpty()) {
				user.setPhoneNumber(phoneNumber);
			}
			//set the new parameters to the user
			UserDao.getInstance().updateUserInDB(user);
		} else {
			throw new Exception("Invalid password for update profile!");
		}
		return "redirect:/profile/" + user.getUsername();
	}

	@RequestMapping(value = "/follow/{username}", method = RequestMethod.GET)
	public String followUser(@PathVariable("username") String folowingUsername, Model model, HttpSession session) throws Exception {
		User folowingUser = UserDao.getInstance().generateUser(folowingUsername);
		User folowerUser = (User) session.getAttribute("user");

		UserDao.getInstance().followUser(folowerUser, folowingUser);
		if(folowingUser != null) {
			model.addAttribute("user", folowingUser);
		}else {
			throw new Exception("Invalid follow user");
		}
		
		
		return "redirect:/profile/" + folowingUser.getUsername();
	}

	@RequestMapping(value = "/unfollow/{username}", method = RequestMethod.GET)
	public String unFollowUser(@PathVariable("username") String folowingUsername, Model model, HttpSession session) throws Exception {
		User folowingUser = UserDao.getInstance().generateUser(folowingUsername);
		User folowerUser = (User) session.getAttribute("user");
		if(folowingUser == null) {
			throw new Exception("Invalid unfollow user");
		}
		UserDao.getInstance().unFollowUser(folowerUser, folowingUser);

		model.addAttribute("user", folowingUser);
		return "redirect:/profile/" + folowingUser.getUsername();
	}

}
