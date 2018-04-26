package bg.VOB.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bg.VOB.controller.UserManager;
import bg.VOB.model.User;

@WebServlet("/RegisterSurvlet")
public class RegisterSurvlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		int age = Integer.parseInt(request.getParameter("age"));

		UserManager.getInstance().registration(new User(username, password, email, phone, age));

		request.getRequestDispatcher("index.jsp").forward(request, response);
		
	}

}
