package bg.VOB.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

	public Video uploadVideo(User u, String name, String description, String path) throws InvalidUserDataException {
		Video v = new Video(name,description);
		saveVideoInDB(u, v, path);
		return v;
	}
	
	public void saveVideoInDB(User u, Video v, String path) {
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
		} catch (SQLException e) { // TODO: UploadingVideoException - no name, wrong path
			System.out.println("DB error: " + e.getMessage());
		}
	}

	
	public Video getVideoByUserAndName(User u, String name) {
		String sql = "SELECT id FROM video WHERE user_id = ? AND name = ?";
		try (PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, u.getId());
			ps.setString(2, name);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				return new Video(rs.getInt(1), name);
			}
		} catch (SQLException e) {
			System.out.println("DB error: " + e.getMessage());
		}
		return null;
	}

	//see the current user upload history
	public ArrayList<Video> getAllVideosByUser(User u){
		ArrayList<Video> userVideos = null;
		try (PreparedStatement ps = connection.prepareStatement("SELECT id,name FROM video WHERE user_id = ?");) {
			ps.setInt(1, u.getId());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				userVideos.add(new Video(rs.getInt("id"), rs.getString("name")));
			}
		} catch (SQLException e) {
			System.out.println("DB error: " + e.getMessage());
		}
		
		return userVideos;
	}
	
	public Video getVideoByName(String name) {
		String sql = "SELECT id FROM video WHERE name = ?";
		try (PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				return new Video(rs.getInt(1), name);
			}
		} catch (SQLException e) {
			System.out.println("DB error: " + e.getMessage());
		}
		return null;
	}

	public void likeVideo(User u, String videoName) {
		String sql;
		Video v = getVideoByName(videoName);
		//see if the video is allready liked or disliked by the user
		if (!isVideoLikedDislikedInDB(u, videoName)) {
			sql = "INSERT INTO video_like_dislike(user_id, video_id, liked_disliked) VALUES (?,?,?)";
			try (PreparedStatement ps = connection.prepareStatement(sql);) {
				ps.setInt(1, u.getId());
				ps.setInt(2, v.getId());
				ps.setInt(3, 1);
				ps.executeUpdate();

			} catch (SQLException e) {
				System.out.println("DB error: " + e.getMessage());
			}
		} else {
			//check is the video liked or disliked by the user
			int existAs = getLikedDisliked(u, videoName);
			if (existAs == 1) {
				sql = "UPDATE video_like_dislike SET liked_disliked = 0 WHERE user_id = ? AND video_id = ?";
				try (PreparedStatement ps = connection.prepareStatement(sql);) {
					ps.setInt(1, u.getId());
					ps.setInt(2, v.getId());
					ps.executeUpdate();
				} catch (SQLException e) {
					System.out.println("DB error: " + e.getMessage());
				}
			} else {
				sql = "UPDATE video_like_dislike SET liked_disliked = 1 WHERE user_id = ? AND video_id = ?";
				try (PreparedStatement ps = connection.prepareStatement(sql);) {
					ps.setInt(1, u.getId());
					ps.setInt(2, v.getId());
					ps.executeUpdate();
				} catch (SQLException e) {
					System.out.println("DB error: " + e.getMessage());
				}
			}
		}
	}

	public void dislikeVideo(User u, String videoName) {
		String sql;
		Video v = getVideoByName(videoName);
		//see if the video is allready liked or disliked by the user
		if (!isVideoLikedDislikedInDB(u, videoName)) {
			sql = "INSERT INTO video_like_dislike(user_id, video_id, liked_disliked) VALUES (?,?,?)";
			try (PreparedStatement ps = connection.prepareStatement(sql);) {
				ps.setInt(1, u.getId());
				ps.setInt(2, v.getId());
				ps.setInt(3, -1);
				ps.executeUpdate();

			} catch (SQLException e) {
				System.out.println("DB error: " + e.getMessage());
			}
		} else {
			//check is the video liked or disliked by the user
			int existAs = getLikedDisliked(u, videoName);
			if (existAs == -1) {
				sql = "UPDATE video_like_dislike SET liked_disliked = 0 WHERE user_id = ? AND video_id = ?";
				try (PreparedStatement ps = connection.prepareStatement(sql);) {
					ps.setInt(1, u.getId());
					ps.setInt(2, v.getId());
					ps.executeUpdate();
				} catch (SQLException e) {
					System.out.println("DB error: " + e.getMessage());
				}
			} else {
				sql = "UPDATE video_like_dislike SET liked_disliked = -1 WHERE user_id = ? AND video_id = ?";
				try (PreparedStatement ps = connection.prepareStatement(sql);) {
					ps.setInt(1, u.getId());
					ps.setInt(2, v.getId());
					ps.executeUpdate();
				} catch (SQLException e) {
					System.out.println("DB error: " + e.getMessage());
				}
			}
		}
	}
	
	//TODO make it private if its not used anywhere else
	public int getLikedDisliked(User u, String videoName) {
		String sql = "SELECT liked_disliked FROM video_like_dislike WHERE user_id = ? AND video_id = ?";
		Video v = VideoDao.getInstance().getVideoByName(videoName);
		try (PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, u.getId());
			ps.setInt(2, v.getId());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("DB error: " + e.getMessage());
		}
		return 0;
	}
	
	//TODO make it private if its not used anywhere else
	public boolean isVideoLikedDislikedInDB(User u, String videoName) {
		String sql = "SELECT liked_disliked FROM video_like_dislike WHERE user_id = ? AND video_id = ?";
		Video v = VideoDao.getInstance().getVideoByName(videoName);
		boolean exists = false;
		try (PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, u.getId());
			ps.setInt(2, v.getId());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return exists = true;
			}
		} catch (SQLException e) {
			System.out.println("DB error: " + e.getMessage());
		}
		return exists;
	}
	
	@Override
	public ArrayList<Video> getAllVideos() {
		ArrayList<Video> allVideos = new ArrayList<>();
		String sql = "SELECT id,name,date,views,user_id,description,path FROM video";
		
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				allVideos.add(new Video(rs.getInt("id"), rs.getString("name"), rs.getTimestamp("date").toLocalDateTime(), rs.getInt("views"), 0, 0, rs.getString("description"),rs.getString("path")));
			}
			
		} catch (SQLException e) {
			System.out.println("DB error: " + e.getMessage());
		}
		
		return allVideos;
	}
}
