package bg.VOB.controller;

import bg.VOB.model.Playlist;
import bg.VOB.model.User;
import bg.VOB.model.Video;
import bg.VOB.model.dao.CommentDao;
import bg.VOB.model.dao.PlaylistDao;
import bg.VOB.model.dao.UserDao;
import bg.VOB.model.dao.VideoDao;
import util.exceptions.InvalidUserDataException;


public class UserManager {

	private static UserManager instance;
	private static UserDao userDao = UserDao.getInstance();
	
	private UserManager() {}
	
	public static synchronized UserManager getInstance() {
		if(instance == null) {
			instance = new UserManager();
		}
		return instance;
	}
	
	public synchronized void registration(User u) {
		UserDao.getInstance().saveUserInDB(u);
		
	}
	
	public User signIn(String username, String password) throws InvalidUserDataException {
			if(UserDao.getInstance().checkForUser(username,password)) {
				User u = UserDao.getInstance().generateUser(username);
				return u;
			}
			else {
				throw new InvalidUserDataException("Invalid username or password");
			}
	}

	public synchronized void addVideo(User u, String name, String description, String path) throws InvalidUserDataException {
		VideoDao.getInstance().uploadVideo(u, name, description, path);
	}
	
	public synchronized void addPlaylist(User u, String name) {
		Playlist p = PlaylistDao.getInstance().addPlaylist(u, name);
	}
	
	public synchronized void addVideoToPlaylist(User u, String videoName, String playlistName) {
		PlaylistDao.getInstance().addVideoToPlaylist(u, videoName, playlistName);
	}
	
	public synchronized void likeVideo(User u, String videoName) {
		VideoDao.getInstance().likeVideo(u, videoName);
	}
	
	public synchronized void dislikeVideo(User u, String videoName) {
		VideoDao.getInstance().dislikeVideo(u, videoName);
	}
	
	public synchronized void comment(User u, String videoName, String content) throws InvalidUserDataException {
		CommentDao.getInstance().addComment(u, videoName, content);
	}
	
	public synchronized void editComment(User u, String videoName, int commentId, String content) throws InvalidUserDataException {
		CommentDao.getInstance().editComment(u, videoName, commentId, content);
	}
	
	public synchronized void deleteComment(User u, String videoName, int commentId) {
		CommentDao.getInstance().deleteComment(u, videoName, commentId);
	}

	public Video getVideo(int id) {
		return VideoDao.getInstance().getVideoById(id);
	}
	
	public User getUserById(int id) {
		return UserDao.getInstance().generateUserById(id);
	}
	
	public int getVideoLikes(int videoId) {
		return VideoDao.getInstance().getVideoLikes(videoId);
	}
	
	public int getVideoDislikes(int videoId) {
		return VideoDao.getInstance().getVideoDislikes(videoId);
	}
	
	public void updateVideoViews(int videoId) {
		VideoDao.getInstance().updateVideoViews(videoId);
	}
	
	public int getVideoViews(int videoId) {
		return VideoDao.getInstance().getVideoViews(videoId);
	}
}
