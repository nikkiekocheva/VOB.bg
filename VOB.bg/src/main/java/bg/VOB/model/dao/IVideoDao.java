package bg.VOB.model.dao;

import java.util.ArrayList;

import bg.VOB.model.User;
import bg.VOB.model.Video;
import util.exceptions.InvalidUserDataException;

public interface IVideoDao {

	Video uploadVideo(User u, String name, String description, String path) throws InvalidUserDataException;

	void saveVideoInDB(User u, Video v, String path);

	Video getVideoById(int id);

	public Video getVideoByUserAndName(User u, String name);

	public Video getVideoByName(String name);

	public void likeVideo(User u, String videoName);

	public void dislikeVideo(User u, String videoName);

	public int getLikedDisliked(User u, String videoName);

	public boolean isVideoLikedDislikedInDB(User u, String videoName);

	int getVideoLikes(int id);

	int getVideoDislikes(int id);

	ArrayList<Video> getAllVideosByUser(User u);

	ArrayList<Video> getAllVideos();

	ArrayList<Video> searchForVideos(String text);

	int getVideoViews(int id);

	void updateVideoViews(int videoId);
}
