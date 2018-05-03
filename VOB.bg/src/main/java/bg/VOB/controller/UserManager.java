package bg.VOB.controller;

import java.sql.SQLException;

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
	
	public synchronized void registration(User u) throws SQLException {
		UserDao.getInstance().saveUserInDB(u);
		
	}
	
	public User signIn(String username, String password) throws Exception {
			if(UserDao.getInstance().checkForUser(username,password)) {
				User u = UserDao.getInstance().generateUser(username);
				if(u == null) {
					throw new InvalidUserDataException("Invalid username");
				}
				return u;
			}
			else {
				throw new InvalidUserDataException("Invalid username or password");
			}
	}

	public synchronized void addVideo(User u, String name, String description, String path) throws InvalidUserDataException, SQLException {
		VideoDao.getInstance().uploadVideo(u, name, description, path);
	}
	
	public synchronized void addPlaylist(User u, String name) throws SQLException{
		Playlist p = PlaylistDao.getInstance().addPlaylist(u, name);
	}
	
	public synchronized void likeVideo(User u, int videoId) throws SQLException{
		VideoDao.getInstance().likeVideo(u, videoId);
	}
	
	public synchronized void dislikeVideo(User u, int videoId) throws SQLException {
		VideoDao.getInstance().dislikeVideo(u, videoId);
	}
	
	public synchronized void comment(User u, int videoId, String content) throws Exception{
		try {
			CommentDao.getInstance().addComment(u, videoId, content);
		} catch (InvalidUserDataException e) {
			throw new Exception(e.getMessage());
		}
	}
	
	public synchronized void editComment(User u, int videoId, int commentId, String content){
		try {
			CommentDao.getInstance().editComment(u, videoId, commentId, content);
		} catch (InvalidUserDataException e) {
			e.getMessage();
		}
	}
	
	public synchronized void deleteComment(User u, int videoId, int commentId) throws SQLException{
		CommentDao.getInstance().deleteComment(u, videoId, commentId);
	}

	public Video getVideo(int id) throws SQLException {
		return VideoDao.getInstance().getVideoById(id);
	}
	
	public User getUserById(int id) throws Exception {
		return UserDao.getInstance().generateUserById(id);
	}
	
	public int getVideoLikes(int videoId) throws SQLException {
		return VideoDao.getInstance().getVideoLikes(videoId);
	}
	
	public int getVideoDislikes(int videoId) throws SQLException {
		return VideoDao.getInstance().getVideoDislikes(videoId);
	}
	
	public void updateVideoViews(int videoId) throws SQLException {
		VideoDao.getInstance().updateVideoViews(videoId);
	}
	
	public int getVideoViews(int videoId) throws SQLException {
		return VideoDao.getInstance().getVideoViews(videoId);
	}
}
