package bg.VOB.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import bg.VOB.SpringWebConfig;
import bg.VOB.WebInitializer;
import bg.VOB.model.Comment;
import bg.VOB.model.User;
import bg.VOB.model.Video;
import bg.VOB.model.dao.CommentDao;
import bg.VOB.model.dao.PlaylistDao;
import bg.VOB.model.dao.UserDao;
import bg.VOB.model.dao.VideoDao;
import util.exceptions.InvalidUserDataException;

@Controller
@MultipartConfig
public class VideoController {

	@RequestMapping(value = "/uploadVideo", method = RequestMethod.GET)
	public String uploadVideo() {
		return "uploadVideo";
	}

	@RequestMapping(value = "/uploadVideo", method = RequestMethod.POST)
	public String saveVideo(@RequestParam("videoFile") MultipartFile file, HttpSession session,
			HttpServletRequest request) throws Exception {
		User user = (User) session.getAttribute("user");
		String filename = user.getUsername() + file.getOriginalFilename();
		String path = SpringWebConfig.LOCATION + File.separator + filename;
		File f = new File(path); // username+photoname
		try {
			file.transferTo(f);
			UserManager.getInstance().addVideo(user, request.getParameter("name"), request.getParameter("description"),
					filename);
		} catch (Exception e) {
			throw new Exception("Invalid file saving: " + e.getMessage());
		}

		return "uploadVideo";
	}

	@RequestMapping(value = "/videos", method = RequestMethod.GET)
	public String showVideosPage(Model model, HttpServletResponse response) throws SQLException {
		// get all the videos in the DB
		ArrayList<Video> allVideosList = VideoDao.getInstance().getAllVideos();
		model.addAttribute("allVideos", allVideosList);

		return "allvideos";
	}
	
	@RequestMapping(value = "/videos", method = RequestMethod.POST)
	public String orderVideosInPage(Model model, HttpServletRequest request) throws SQLException {
		String orderType = request.getParameter("type");
		
		ArrayList<Video> allVideosList = VideoDao.getInstance().getAllVideosOrdered(orderType);
		System.out.println(orderType);
		
		model.addAttribute("allVideos", allVideosList);
		return "allvideos";
	}
	

	@RequestMapping(value = "/videos/{video.path:.+}", method = RequestMethod.GET)
	public void showVideos(Model model, @PathVariable("video.path") String path, HttpServletResponse response) {
		// get all the videos in the DB
		File f = new File(SpringWebConfig.LOCATION + path);
		try {
			ServletOutputStream os = response.getOutputStream();
			Files.copy(f.toPath(), os);
			os.flush();

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	@RequestMapping(value = "/view/{video.id}", method = RequestMethod.GET)
	public String view(Model model, @PathVariable("video.id") int id, HttpServletResponse response) throws SQLException {
		Video v = UserManager.getInstance().getVideo(id);
		UserManager.getInstance().updateVideoViews(id);
		User u = UserManager.getInstance().getUserById(v.getUserId());
		int likes = UserManager.getInstance().getVideoLikes(v.getId());
		int dislikes = UserManager.getInstance().getVideoDislikes(v.getId());
		int views = UserManager.getInstance().getVideoViews(v.getId());
		ArrayList<Comment> allCommentsList = CommentDao.getInstance().getAllComments(id);
		model.addAttribute("localDateTimeFormat", new SimpleDateFormat("MM/dd/yyyy HH:mm:ss"));
		model.addAttribute("allComments", allCommentsList);
		model.addAttribute("video", v);
		model.addAttribute("videouser", u);
		model.addAttribute("likes", likes);
		model.addAttribute("dislikes", dislikes);
		model.addAttribute("views", views);
		return "viewVideo";
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String searchFor(HttpServletRequest request, Model model) throws SQLException {
		String searchText = request.getParameter("text");
		String searchType = request.getParameter("type");
		ArrayList found = new ArrayList<>();

		switch (searchType) {
		case "user":
			found = UserDao.getInstance().searchForUser(searchText);
			model.addAttribute("type", 1);
			break;
		case "video":
			found = VideoDao.getInstance().searchForVideos(searchText);
			model.addAttribute("type", 2);
			break;
		case "playlist":
			found = PlaylistDao.getInstance().searchForPlaylist(searchText);
			model.addAttribute("type", 3);
			break;
		}

		System.out.println(found.toString());
		model.addAttribute("found", found);

		return "search";
	}
	
	@RequestMapping(value="/rateVideo/{video.id}", method = RequestMethod.GET)
	public String dislikeVideo(HttpSession session, @PathVariable("video.id") int id, HttpServletRequest request) throws SQLException{
		Video v = UserManager.getInstance().getVideo(id);
		User user = (User)session.getAttribute("user");
		String button = request.getParameter("button");
		if(button.equals("button1")) {
			UserManager.getInstance().likeVideo(user, id);
		}
		if(button.equals("button2")) {
			UserManager.getInstance().dislikeVideo(user, id);
		}
		return "redirect:/view/{video.id}";
	}


	
}
