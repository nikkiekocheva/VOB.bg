package bg.VOB.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.spi.FileTypeDetector;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ServiceLoader;

import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.apache.commons.io.FilenameUtils;
import org.jcodec.api.JCodecException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
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
import util.validation.VideoFrameExtracter;

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
		//Validate the file type
		if(filename.endsWith(".mp4")){
			String path = SpringWebConfig.LOCATION + File.separator + filename;
			File f = new File(path); 
			try {
				file.transferTo(f);
				String name = request.getParameter("name");
				String description = request.getParameter("description");
				if(name.isEmpty()) {
					name = filename.replaceAll(".mp4", "");
				}
				if(description.isEmpty()) {
					description = filename.replaceAll(".mp4", "");
				}
				VideoDao.getInstance().uploadVideo(user, name, description, filename);
			} catch (Exception e) {
				throw new Exception("Invalid file saving: " + e.getMessage());
			}
			
			//get The frame form video
			 VideoFrameExtracter videoFrameExtracter = new VideoFrameExtracter();
			 
			 //get the video to extract from
		     File videoFile = Paths.get(path).toFile();
		     try {
		    	 //Extract the frame from video
		         File imageFrame = videoFrameExtracter.createThumbnailFromVideo(videoFile, 500);
		         imageFrame.createNewFile();
		         System.out.println("input file name : " + videoFile.getAbsolutePath());
		         System.out.println("output video frame file name  : " + imageFrame.getAbsolutePath());
		         
		         //Create the file in the right dir
		         File nameFile = new File(SpringWebConfig.LOCATION + File.separator + FilenameUtils.removeExtension(filename) + ".png");
		         
		         //Copy the img from temp to the right dir
		         Files.copy(imageFrame.toPath(), nameFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
		     } catch (IOException | JCodecException e) {
		         System.out.println("error occurred while extracting image : " + e.getMessage());
		     }
			
		}else {
			throw new Exception("Only video files are to be uploded!!!!");
		}
		
		return "uploadVideo";
	}

	@RequestMapping(value = "/videos", method = RequestMethod.GET)
	public String showVideosPage(Model model, HttpServletResponse response,HttpSession session) throws SQLException {
		// get all the videos in the data base
		ArrayList<Video> allVideosList = VideoDao.getInstance().getAllVideos();
		
		model.addAttribute("allVideos", allVideosList);
		//get the videos of following users
		User user = (User) session.getAttribute("user");
		ArrayList<User> followingUsers = UserDao.getInstance().getAllFollowingUsers(user);
		HashMap<String, ArrayList<Video>> usersVideos = new HashMap<>();
		for(User u : followingUsers) {
			usersVideos.put(u.getUsername(), VideoDao.getInstance().getAllVideosByUser(u));
		}
		model.addAttribute("usersVideos",usersVideos);
		return "allvideos";
	}
	
	@RequestMapping(value = "/videos", method = RequestMethod.POST)
	public String orderVideosInPage(Model model, HttpServletRequest request) throws SQLException {
		//get what to order the videos by
		String orderType = request.getParameter("type");
		//get all the videos ordered
		ArrayList<Video> allVideosList = VideoDao.getInstance().getAllVideosOrdered(orderType);
		
		model.addAttribute("allVideos", allVideosList);
		return "allvideos";
	}
	

	@RequestMapping(value = "/videos/{video.path:.+}", method = RequestMethod.GET)
	public void showVideos(Model model, @PathVariable("video.path") String path, HttpServletResponse response) {
		// get all the videos in the DB
		File f = new File(SpringWebConfig.LOCATION + path);
		try {
			ServletOutputStream os = response.getOutputStream();
			Files.copy(f.toPath(), response.getOutputStream());
			os.flush();
			
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	@RequestMapping(value = "/view/{video.id}", method = RequestMethod.GET)
	public String view(Model model, @PathVariable("video.id") int id, HttpServletResponse response,HttpSession session) throws Exception {
		//get the video that will be viewed
		Video v = UserManager.getInstance().getVideo(id);
		//get the user watching the video
		User watcherUser = (User) session.getAttribute("user");
		
		//Check if the user has viewed the video soon
		if(session.getAttribute(v.getPath()) == null) {
			//if the user haven't watched it this session soon increment the views
			VideoDao.getInstance().updateVideoViews(id);
			v = UserManager.getInstance().getVideo(id);
			session.setAttribute(v.getPath(), v.getPath());
			ViewsCheckerManager.getInstance().addVideoForViewsBlock(v.getPath(), watcherUser.getUsername());
		}else {
			//if the time for view blocking has expired increment the view again
			if(ViewsCheckerManager.getInstance().areVideoViewsForIncrement(v.getPath(),watcherUser.getUsername())) {
				UserManager.getInstance().updateVideoViews(id);
				v = UserManager.getInstance().getVideo(id);
			}
		}
		//get the owner of the video and the likes and dislikes of video
		User videoUser = UserManager.getInstance().getUserById(v.getUserId());
		int videoLikes = VideoDao.getInstance().getVideoLikes(v.getId());
		int videoDislikes = VideoDao.getInstance().getVideoDislikes(v.getId());
		
		//Check if the watching user has liked or disliked the video
		int userLikeDislike = VideoDao.getInstance().getLikedDisliked(watcherUser, v.getId());
		
		//get all the comments of the video
		ArrayList<Comment> allCommentsList = CommentDao.getInstance().getAllComments(id);
		
		//add the attributes needed to view the video
		model.addAttribute("localDateTimeFormat", new SimpleDateFormat("MM/dd/yyyy HH:mm:ss"));
		model.addAttribute("allComments", allCommentsList);
		model.addAttribute("video", v);
		model.addAttribute("videouser", videoUser);
		model.addAttribute("likes", videoLikes);
		model.addAttribute("dislikes", videoDislikes);
		model.addAttribute("userLikeDislike", userLikeDislike);
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
			break;
		case "video":
			found = VideoDao.getInstance().searchForVideos(searchText);
			break;
		case "playlist":
			found = PlaylistDao.getInstance().searchForPlaylist(searchText);
			break;
		}
		model.addAttribute("type", searchType);
		model.addAttribute("found", found);
		if(found.isEmpty()) {
			model.addAttribute("nothingfound",true);
		}
		return "search";
	}
	
	@RequestMapping(value="/rateVideo/{video.id}", method = RequestMethod.GET)
	public String dislikeVideo(HttpSession session, @PathVariable("video.id") int id, HttpServletRequest request) throws SQLException{
		Video v = UserManager.getInstance().getVideo(id);
		User user = (User)session.getAttribute("user");
		String button = request.getParameter("button");
		
		if(button.equals("buttonlike")) {
			VideoDao.getInstance().likeVideo(user, id);
		}
		if(button.equals("buttondislike")) {
			VideoDao.getInstance().dislikeVideo(user, id);
		}
		
		return "redirect:/view/{video.id}";
	}
	
	@RequestMapping(value = "/views/{video.name}", method = RequestMethod.GET)
	public String viewFoundVideo(Model model, @PathVariable("video.name") String videoName, HttpServletResponse response) throws SQLException {
		Video v = VideoDao.getInstance().getVideoByName(videoName);
		return "redirect:/view/" + v.getId();
	}

}
