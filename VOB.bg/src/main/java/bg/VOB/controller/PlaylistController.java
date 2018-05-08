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
		//Get the all the playlists of user
		ArrayList<Playlist> playlists = PlaylistDao.getInstance().getUserPlaylists(user);
		
		model.addAttribute("username",user.getUsername());
		model.addAttribute("playlists",playlists);
		return "playlist";
	}
	
	@RequestMapping(value = "/playlist/{playlist.name}", method = RequestMethod.GET)
	public String showPlaylistOfUser(@PathVariable("playlist.name") String name, Model model, HttpSession session) throws Exception{
		//Get the playlist and the user it belongs to
		Playlist playlist = PlaylistDao.getInstance().getPLaylistByName(name);
		User user = UserDao.getInstance().generateUserById(playlist.getUserId());
		//Get the videos in the playlist
		ArrayList<Video> videos = PlaylistDao.getInstance().getVideosFromPlaylist(playlist);
		
		model.addAttribute("username",user.getUsername());
		model.addAttribute("videos",videos);
		model.addAttribute("playlist",playlist);
		return "playlistvideos";
	}
	
	
	@RequestMapping(value = "/playlist", method = RequestMethod.POST)
	public String makeNewPlaylist(Model model,HttpServletRequest request,HttpSession session) throws SQLException{
		User u = (User) session.getAttribute("user");
		String playlistName = request.getParameter("name");
		
		if(!playlistName.isEmpty()) {
			PlaylistDao.getInstance().addPlaylist(u, playlistName);
		}
		
		return "redirect:/playlist";
	}
	
	@RequestMapping(value = "/videotoplaylist", method = RequestMethod.POST)
	public String getAllThePlaylist(Model model,HttpServletRequest request,HttpSession session) throws SQLException{
		int videoId = Integer.parseInt(request.getParameter("videoid"));
		User u = (User) session.getAttribute("user");
		session.setAttribute("videoidtoadd", videoId);
		
		//get the playlis of the user and add them to the model
		ArrayList<Playlist> allUserPlaylists = PlaylistDao.getInstance().getAllPLaylistOfUser(u);
		model.addAttribute("allplaylists", allUserPlaylists);
	
		return "addtoplaylist";
	}
	
	@RequestMapping(value = "/addtoplaylist", method = RequestMethod.POST)
	public String addVideoToPlaylist(Model model,HttpServletRequest request,HttpSession session) throws SQLException{
		//get the playlist chosen
		String playlistName = request.getParameter("list");
		Playlist p = PlaylistDao.getInstance().getPLaylistByName(playlistName);
		int videoId = (Integer) session.getAttribute("videoidtoadd");
		//if playlist is found and if the video is not in the playlist, add it
		if(p != null) {
			if(!PlaylistDao.getInstance().checkIfVideoIsInPlaylist(p,videoId )) {
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
		String playListName = request.getParameter("playlist");
		
		User u = (User) session.getAttribute("user");
		Playlist p = PlaylistDao.getInstance().getPLaylistByName(playListName);
		
		if(p != null) {
			PlaylistDao.getInstance().removeVideoFromPlaylistInDB(p, videoId);
		}
		
		return "redirect:/playlist";
	}
}
