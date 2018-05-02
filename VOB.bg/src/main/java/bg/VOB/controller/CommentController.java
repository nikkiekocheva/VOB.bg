package bg.VOB.controller;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import bg.VOB.model.Comment;
import bg.VOB.model.User;
import bg.VOB.model.Video;
import bg.VOB.model.dao.CommentDao;
import bg.VOB.model.dao.VideoDao;

@Controller
@MultipartConfig
public class CommentController {

	@RequestMapping(value = "/addComment/{video.id}", method = RequestMethod.GET)
	public String orderVideosInPage(HttpSession session, @PathVariable("video.id") int id, HttpServletRequest request) throws SQLException {
		Video v = UserManager.getInstance().getVideo(id);
		User user = (User)session.getAttribute("user");
		String text = request.getParameter("comment");
		UserManager.getInstance().comment(user, id, text);
		
		return "redirect:/view/{video.id}";
	}

}
