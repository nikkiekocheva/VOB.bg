package bg.VOB.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {

	@ExceptionHandler(value=Exception.class)
	public String error(HttpServletRequest request,Exception e,HttpSession session) {
		request.setAttribute("exception", e);
		if(session.getAttribute("user") == null) {
			request.setAttribute("newSession", true);
		}else{
			request.setAttribute("newSession", false);
		}
		return "error";
	}
	
}
