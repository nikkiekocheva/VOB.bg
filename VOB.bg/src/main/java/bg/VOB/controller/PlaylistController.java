package bg.VOB.controller;

import java.util.ArrayList;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import bg.VOB.model.Playlist;
import bg.VOB.model.User;
import bg.VOB.model.Video;
import bg.VOB.model.dao.PlaylistDao;
import bg.VOB.model.dao.VideoDao;

@Controller
@MultipartConfig
public class PlaylistController {

	@RequestMapping(value = "/playlist", method = RequestMethod.GET)
	public String showPlaylist(Model model,HttpSession session) {
		User u = (User) session.getAttribute("user");
		ArrayList<Video> videos = PlaylistDao.getInstance().getVideosFromPlaylist(u);
		if(videos == null) {
			videos = new ArrayList<>();
		}
		model.addAttribute("videos",videos);
		return "playlist";
	}
	
	@RequestMapping(value = "/playlist", method = RequestMethod.POST)
	public String makeNewPlaylist(Model model,HttpServletRequest request,HttpSession session) {
		User u = (User) session.getAttribute("user");
		PlaylistDao.getInstance().addPlaylist(u, request.getParameter("name"));
		
		return "playlist";
	}
	
	@RequestMapping(value = "/videotoplaylist", method = RequestMethod.POST)
	public String addVideoToPlaylist(Model model,HttpServletRequest request,HttpSession session) {
		int videoId = Integer.parseInt(request.getParameter("videoid"));
		
		User u = (User) session.getAttribute("user");
		Playlist p = PlaylistDao.getInstance().getPLaylistByUser(u);
		
		if(!PlaylistDao.getInstance().checkIfVideoIsInPlaylist(p, videoId)) {
			PlaylistDao.getInstance().saveVideoInPlaylistInDB(p, videoId);
		}else {
			System.out.println("Video is allready in playlist!!");
		}
		
		return "allvideos";
	}
	
}
