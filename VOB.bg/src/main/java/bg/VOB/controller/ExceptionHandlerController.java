package bg.VOB.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {

	@ExceptionHandler(value=Exception.class)
	public String error(HttpServletRequest request,Exception e) {
		request.setAttribute("exception", e);
		return "error";
	}
	
}
