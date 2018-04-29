package bg.VOB.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import bg.VOB.SpringWebConfig;
import bg.VOB.WebInitializer;
import bg.VOB.model.User;
import bg.VOB.model.Video;
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
	public String saveVideo(@RequestParam("videoFile") MultipartFile file, HttpSession session, HttpServletRequest request) {
		User user = (User) session.getAttribute("user");
		String path = SpringWebConfig.LOCATION + File.separator + user.getUsername() + file.getOriginalFilename();
		File f = new File(path); //username+photoname
		try {
			file.transferTo(f);
			UserManager.getInstance().addVideo(user, request.getParameter("name"), request.getParameter("description"), path);
		} catch (IllegalStateException e) {
			System.out.println("Invalid file saving.");
		} catch (IOException e) {
			System.out.println("Invalid file saving.");
		} catch (InvalidUserDataException e) {
			System.out.println("Invalid file saving.");
		}
		
		return "uploadVideo";
	}
	
	@RequestMapping(value = "/videos", method = RequestMethod.GET)
	public String showVideos(Model model) {
		ArrayList<Video> allVideosList = VideoDao.getInstance().getAllVideos();
		System.out.println(allVideosList.get(0).getPath());
		model.addAttribute("allVideos",allVideosList);
		
		return "allvideos";
	}
}