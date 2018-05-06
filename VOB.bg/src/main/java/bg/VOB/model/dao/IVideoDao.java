package bg.VOB.model.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import bg.VOB.model.User;
import bg.VOB.model.Video;
import util.exceptions.InvalidUserDataException;

public interface IVideoDao {

	Video uploadVideo(User u, String name, String description, String path) throws InvalidUserDataException, SQLException;

	void saveVideoInDB(User u, Video v, String path) throws SQLException;

	Video getVideoById(int id) throws SQLException;

	public Video getVideoByUserAndName(User u, String name) throws SQLException;

	public Video getVideoByName(String name) throws SQLException;

	public void likeVideo(User u, int videoId) throws SQLException;

	public void dislikeVideo(User u, int videoId) throws SQLException;

	public int getLikedDisliked(User u, int videoId) throws SQLException;

	public boolean isVideoLikedDislikedInDB(User u, int videoId) throws SQLException;

	int getVideoLikes(int id) throws SQLException;

	int getVideoDislikes(int id) throws SQLException;

	ArrayList<Video> getAllVideosByUser(User u) throws SQLException;

	ArrayList<Video> getAllVideos() throws SQLException;

	ArrayList<Video> searchForVideos(String text) throws SQLException;

	int getVideoViews(int id) throws SQLException;

	void updateVideoViews(int videoId) throws SQLException;
	
	ArrayList<Video> getAllVideosOrdered(String orderBY) throws SQLException;
}
