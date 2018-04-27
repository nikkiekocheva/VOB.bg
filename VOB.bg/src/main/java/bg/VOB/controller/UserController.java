package bg.VOB.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import bg.VOB.model.User;
import util.exceptions.InvalidUserDataException;

@Controller
public class UserController {

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String showHomePage() {
		return "index";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginUser(HttpServletRequest req) {
		// get the username and password
		String username = req.getParameter("username");
		String password = req.getParameter("password");

		// check for if the input values are in the db
		try {
			if (UserManager.getInstance().signIn(username, password) != null) {
				// make a session
				HttpSession session = req.getSession();
				session.setAttribute("username", username);
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
	public String userLogout(HttpSession session){
		//Invalidate the session
		session.invalidate();
		//Return to the index page
		return "index";
	}
}
