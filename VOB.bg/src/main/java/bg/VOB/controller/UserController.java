package bg.VOB.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import bg.VOB.model.User;
import bg.VOB.model.Video;
import bg.VOB.model.dao.UserDao;
import bg.VOB.model.dao.VideoDao;
import util.exceptions.InvalidUserDataException;

@Controller
public class UserController {

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String showHomePage() {
		return "index";
	}

	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String showMainPage() {
		return "main";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginUser(HttpServletRequest req) {
		// get the username and password
		String username = req.getParameter("username");
		String password = req.getParameter("password");

		// check for if the input values are in the db
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

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerUser(HttpServletRequest request) {

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		int age = Integer.parseInt(request.getParameter("age"));

		UserManager.getInstance().registration(new User(username, password, email, phone, age));

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
	public String showUserProfile(@PathVariable("username") String username, Model model) {
		User user = UserDao.getInstance().generateUser(username);
		ArrayList<Video> userVideos = VideoDao.getInstance().getAllVideosByUser(user);
		model.addAttribute("userVideos", userVideos);

		return "profile";
	}

	@RequestMapping(value = "/profile", method = RequestMethod.POST)
	public String showUpdateUserProfile() {
		return "updateProfile";
	}

	@RequestMapping(value = "/updateprofile", method = RequestMethod.POST)
	public String updateUserProfile(HttpServletRequest request, HttpSession session) {
		User user = (User) session.getAttribute("user");

		String email = request.getParameter("email");
		String phoneNumber = request.getParameter("phone");
		String newPassword = request.getParameter("newpassword");
		String newPassword1 = request.getParameter("newpassword1");
		String currentPassword = request.getParameter("currentpassword");

		if (user.getPassword() != null && BCrypt.checkpw(currentPassword, user.getPassword())) {
			if (!(newPassword.isEmpty() && newPassword1.isEmpty())) {
				if (!newPassword.equals(newPassword1)) {
					request.setAttribute("error", "The new passwords dont match!!! ");
					return "error";
				}
				user.setPassword(newPassword);
			}
			if (!email.isEmpty()) {
				user.setEmail(email);
			}
			if (!phoneNumber.isEmpty()) {
				user.setPhoneNumber(phoneNumber);
			}
			UserDao.getInstance().updateUserInDB(user);
		} else {
			request.setAttribute("error", "Wrong password!!");
			return "error";
		}
		return "profile";
	}

}
