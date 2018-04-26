package bg.VOB.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bg.VOB.controller.UserManager;
import util.exceptions.InvalidUserDataException;

@WebServlet("/Login")
public class LoginSurvlet extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//get the username and password
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
		//check for if the input values are in the db
		try {
			if (UserManager.getInstance().signIn(username, password) != null) {
				//make a session
				HttpSession session = req.getSession();
				session.setAttribute("username", username);
				//forward to the main page
				req.getRequestDispatcher("WEB-INF/main.jsp").forward(req, resp);
			}
		} catch (InvalidUserDataException e) {
			resp.sendRedirect("WEB-INF/InvalidLogin.html");
		}
	
	}
	
}
