package bg.VOB.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import bg.VOB.controller.DBManager;
import bg.VOB.model.Playlist;
import bg.VOB.model.User;
import bg.VOB.model.Video;
import util.exceptions.InvalidUserDataException;

public class VideoDao implements IVideoDao {

	private static VideoDao instance;
	private Connection connection;

	private VideoDao() {
		connection = DBManager.getInstance().getConnection();
	}

	public synchronized static VideoDao getInstance() {
		if (instance == null) {
			instance = new VideoDao();
		}
		return instance;
	}

	@Override
	public Video uploadVideo(User u, String name, String description, String path) throws InvalidUserDataException, SQLException {
		Video v = new Video(name, description);
		saveVideoInDB(u, v, path);
		v = getVideoByName(name);
		saveVideoLikes(v, u);
		return v;
	}

	@Override
	public void saveVideoInDB(User u, Video v, String path) throws SQLException{
		String sql = "INSERT INTO video(name, date, views, user_id, description, path) VALUES(?,?,0,?,?,?)";
		Date date = new Date();
		Object param = new Timestamp(date.getTime());
		try (PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setString(1, v.getName());
			ps.setObject(2, param);
			ps.setInt(3, u.getId());
			ps.setString(4, v.getDescription());
			ps.setString(5, path);
			ps.executeUpdate();
		} 
	}

	private void saveVideoLikes(Video v,User u) throws SQLException {
		String sql = "INSERT INTO video_like_dislike (user_id, video_id, liked_disliked) VALUES(?,?,?)";
		try (PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, u.getId());
			ps.setInt(2, v.getId());
			ps.setInt(3, 0);
			ps.executeUpdate();
		} 
	}
	
	@Override
	public Video getVideoById(int id) throws SQLException{
		String sql = "SELECT id,name, date, views, user_id, description, path FROM video WHERE id = ?";
		try (PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				return new Video(rs.getInt("id"), rs.getString("name"), rs.getTimestamp("date").toLocalDateTime(), rs.getInt("user_id"),
						rs.getInt("views"), rs.getString("description"), rs.getString("path"));
			}
		}
		return null;
	}

	@Override
	public Video getVideoByUserAndName(User u, String name) throws SQLException{
		String sql = "SELECT id FROM video WHERE user_id = ? AND name = ?";
		try (PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, u.getId());
			ps.setString(2, name);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				return new Video(rs.getInt("id"), name);
			}
		}
		return null;
	}

	// see the current user upload history
	@Override
	public ArrayList<Video> getAllVideosByUser(User u) {
		ArrayList<Video> userVideos = new ArrayList<>();
		try (PreparedStatement ps = connection
				.prepareStatement("SELECT id,name,date,views,user_id,description,path FROM video WHERE user_id = ?");) {
			ps.setInt(1, u.getId());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				userVideos.add(new Video(rs.getInt("id"), rs.getString("name"), rs.getTimestamp("date").toLocalDateTime(),
								rs.getInt("views"), 0, rs.getString("description"), rs.getString("path")));
			}
		} catch (SQLException e) {
			System.out.println("DB error: " + e.getMessage());
		}

		return userVideos;
	}

	@Override
	public Video getVideoByName(String name) throws SQLException {
		String sql = "SELECT id FROM video WHERE name = ?";
		try (PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				return new Video(rs.getInt("id"), name);
			}
		}
		return null;
	}

	@Override
	public void likeVideo(User u, int videoId) throws SQLException{
		String sql;
		Video v = getVideoById(videoId);
		// see if the video is already liked or disliked by the user
		if (!isVideoLikedDislikedInDB(u, videoId)) {
			sql = "INSERT INTO video_like_dislike(user_id, video_id, liked_disliked) VALUES (?,?,?)";
			try (PreparedStatement ps = connection.prepareStatement(sql);) {
				ps.setInt(1, u.getId());
				ps.setInt(2, v.getId());
				ps.setInt(3, 1);
				ps.executeUpdate();

			} 
		} else {
			// check is the video liked or disliked by the user
			int existAs = getLikedDisliked(u,videoId);
			if (existAs == 1) {
				sql = "UPDATE video_like_dislike SET liked_disliked = 0 WHERE user_id = ? AND video_id = ?";
			} else {
				sql = "UPDATE video_like_dislike SET liked_disliked = 1 WHERE user_id = ? AND video_id = ?";
			}

			try (PreparedStatement ps = connection.prepareStatement(sql);) {
				ps.setInt(1, u.getId());
				ps.setInt(2, v.getId());
				ps.executeUpdate();
			} 
		}
	}

	@Override
	public void dislikeVideo(User u, int videoId) throws SQLException {
		String sql;
		// see if the video is already liked or disliked by the user
		if (!isVideoLikedDislikedInDB(u, videoId)) {
			sql = "INSERT INTO video_like_dislike(user_id, video_id, liked_disliked) VALUES (?,?,?)";
			try (PreparedStatement ps = connection.prepareStatement(sql);) {
				ps.setInt(1, u.getId());
				ps.setInt(2, videoId);
				ps.setInt(3, -1);
				ps.executeUpdate();
			} 
		} else {
			// check is the video liked or disliked by the user
			int existAs = getLikedDisliked(u, videoId);
			if (existAs == -1) {
				sql = "UPDATE video_like_dislike SET liked_disliked = 0 WHERE user_id = ? AND video_id = ?";
			} else {
				sql = "UPDATE video_like_dislike SET liked_disliked = -1 WHERE user_id = ? AND video_id = ?";
			}

			try (PreparedStatement ps = connection.prepareStatement(sql);) {
				ps.setInt(1, u.getId());
				ps.setInt(2, videoId);
				ps.executeUpdate();
			} 
		}
	}

	@Override
	public int getLikedDisliked(User u, int videoId) throws SQLException{
		String sql = "SELECT liked_disliked FROM video_like_dislike WHERE user_id = ? AND video_id = ?";
		try (PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, u.getId());
			ps.setInt(2, videoId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} 
		return 0;
	}

	@Override
	public int getVideoLikes(int id) throws SQLException{
		String sql = "SELECT SUM(liked_disliked) FROM video_like_dislike WHERE video_id = ? AND liked_disliked = 1";
		try (PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} 
		return 0;
	}

	@Override
	public int getVideoDislikes(int id) throws SQLException{
		String sql = "SELECT SUM(liked_disliked)*(-1) FROM video_like_dislike WHERE video_id = ? AND liked_disliked = -1";
		try (PreparedStatement ps = connection.prepareStatement(sql);) { 
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} 
		return 0;
	}

	@Override
	public boolean isVideoLikedDislikedInDB(User u, int videoId) throws SQLException{
		String sql = "SELECT liked_disliked FROM video_like_dislike WHERE user_id = ? AND video_id = ?";
		boolean exists = false;
		try (PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, u.getId());
			ps.setInt(2, videoId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return exists = true;
			}
		} 
		return exists;
	}

	@Override
	public void updateVideoViews(int videoId) throws SQLException{
		String sql = "UPDATE video SET views = views + 1 WHERE id = ?";
		try (PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, videoId);
			ps.executeUpdate();
		} 
	}

	@Override
	public int getVideoViews(int id) throws SQLException{
		String sql = "SELECT views FROM video WHERE id = ?";
		try (PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} 
		return 0;
	}

	@Override
	public ArrayList<Video> getAllVideos() throws SQLException{
		ArrayList<Video> allVideos = new ArrayList<>();
		String sql = "SELECT id,name,date,views,user_id,description,path FROM video";

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				allVideos
						.add(new Video(rs.getInt("id"), rs.getString("name"), rs.getTimestamp("date").toLocalDateTime(),
								rs.getInt("views"), 0, rs.getString("description"), rs.getString("path")));
			}

		} 

		return allVideos;
	}

	@Override
	public ArrayList<Video> searchForVideos(String text) throws SQLException{
		ArrayList<Video> matches = new ArrayList<>();
		String sql = "SELECT id, name, date, views, user_id, description, path FROM video WHERE name LIKE ?";

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, "%" + text + "%");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				matches.add(new Video(rs.getInt("id"), rs.getString("name"), rs.getTimestamp("date").toLocalDateTime(),
						rs.getInt("views"), 0, rs.getString("description"), rs.getString("path")));
			}

		} 
		return matches;
	}

	public ArrayList<Video> getAllVideosOrdered(String orderBY) throws SQLException{
		ArrayList<Video> allVideos = new ArrayList<>();
		String sql = "SELECT v.id, v.name, v.date, v.views, v.user_id, v.description, v.path, SUM(l.liked_disliked) AS likes FROM video AS v " + 
						"JOIN video_like_dislike AS l ON v.id = l.video_id GROUP BY v.id;";

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				allVideos.add(new Video(rs.getInt("id"), rs.getString("name"), rs.getTimestamp("date").toLocalDateTime(),
								rs.getInt("user_id"), rs.getInt("views"),rs.getInt("likes"), rs.getString("description"), rs.getString("path")));
			}
		} 
		//Order the collection by views
		Collections.sort(allVideos, new Comparator<Video>() {
			@Override
		    public int compare(Video v1, Video v2) {
				int i = 0;
				if(orderBY.equals("views")) {
					i = v2.getViews() - v1.getViews();
				}
				if(orderBY.equals("date")) {
					i = v2.getDate().compareTo(v1.getDate());
				}
				if(orderBY.equals("like")) {
					i = v2.getLikes() - v1.getLikes();
				}
				return i;
		    }
		});
		return allVideos;
	}
	
}
