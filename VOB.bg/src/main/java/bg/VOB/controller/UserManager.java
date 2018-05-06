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

	public Video getVideo(int id) throws SQLException {
		return VideoDao.getInstance().getVideoById(id);
	}
	
	public User getUserById(int id) throws Exception {
		return UserDao.getInstance().generateUserById(id);
	}
	
	public void updateVideoViews(int videoId) throws SQLException {
		VideoDao.getInstance().updateVideoViews(videoId);
	}
	
}
