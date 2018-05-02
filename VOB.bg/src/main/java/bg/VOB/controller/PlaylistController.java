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

import bg.VOB.model.Playlist;
import bg.VOB.model.User;
import bg.VOB.model.Video;
import bg.VOB.model.dao.PlaylistDao;
import bg.VOB.model.dao.UserDao;
import bg.VOB.model.dao.VideoDao;

@Controller
@MultipartConfig
public class PlaylistController {

	@RequestMapping(value = "/playlist", method = RequestMethod.GET)
	public String showPlaylist(Model model,HttpSession session) throws SQLException{
		
		User user = (User) session.getAttribute("user");
		
		ArrayList<Video> videos = PlaylistDao.getInstance().getVideosFromPlaylist(user);
		if(videos.isEmpty()) {
			videos = new ArrayList<>();
		}
		model.addAttribute("username",user.getUsername());
		model.addAttribute("videos",videos);
		return "playlist";
	}
	
	@RequestMapping(value = "/playlist/{playlist.name}", method = RequestMethod.GET)
	public String showPlaylistOfUser(@PathVariable("playlist.name") String name, Model model, HttpSession session) throws SQLException{
		Playlist p = PlaylistDao.getInstance().getPLaylistByName(name);
		User user = UserDao.getInstance().generateUserById(p.getUserId());
		
		ArrayList<Video> videos = PlaylistDao.getInstance().getVideosFromPlaylist(user);
		if(videos.isEmpty()) {
			videos = new ArrayList<>();
		}
		model.addAttribute("username",user.getUsername());
		model.addAttribute("videos",videos);
		return "playlist";
	}
	
	
	@RequestMapping(value = "/playlist", method = RequestMethod.POST)
	public String makeNewPlaylist(Model model,HttpServletRequest request,HttpSession session) throws SQLException{
		User u = (User) session.getAttribute("user");
		PlaylistDao.getInstance().addPlaylist(u, request.getParameter("name"));
		
		return "redirect:/playlist";
	}
	
	@RequestMapping(value = "/videotoplaylist", method = RequestMethod.POST)
	public String addVideoToPlaylist(Model model,HttpServletRequest request,HttpSession session) throws SQLException{
		int videoId = Integer.parseInt(request.getParameter("videoid"));
		
		User u = (User) session.getAttribute("user");
		Playlist p = PlaylistDao.getInstance().getPLaylistByUser(u);
		
		if(p != null) {
			if(!PlaylistDao.getInstance().checkIfVideoIsInPlaylist(p, videoId)) {
				PlaylistDao.getInstance().saveVideoInPlaylistInDB(p, videoId);
			}else {
				System.out.println("Video is allready in playlist!!");
			}
		}
		return "redirect:/playlist";
	}
	
	@RequestMapping(value = "/removevideoplaylist", method = RequestMethod.POST)
	public String removeVideoFromPlaylist(Model model,HttpServletRequest request,HttpSession session) throws SQLException{
		int videoId = Integer.parseInt(request.getParameter("videoid"));
		
		User u = (User) session.getAttribute("user");
		Playlist p = PlaylistDao.getInstance().getPLaylistByUser(u);
		
		if(p != null) {
			PlaylistDao.getInstance().removeVideoFromPlaylistInDB(p, videoId);
		}
		
		return "redirect:/playlist";
	}
}
