package bg.VOB.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.sun.jmx.snmp.Timestamp;

import bg.VOB.controller.DBManager;
import bg.VOB.model.Playlist;
import bg.VOB.model.User;
import bg.VOB.model.Video;

public class PlaylistDao implements IPlaylistDao{
	
	private static PlaylistDao instance;
	private Connection connection;
	
	private PlaylistDao() {
		connection = DBManager.getInstance().getConnection();
	}
	
	public synchronized static PlaylistDao getInstance() {
		if(instance == null) {
			instance = new PlaylistDao();
		}
		return instance;
	}
	
	//Add a new playlist and save it in DB
	public Playlist addPlaylist(User u, String name) {
		Playlist p = new Playlist(name);
		savePlaylistInDB(u, p);
		return p;
	}
	
	//Save a playlist in the database
	//TODO make it private if its not use anywhere else
	public void savePlaylistInDB(User u, Playlist p) {
		String sql = "INSERT INTO playlist(name, date, user_id) VALUES(?,?,?)";
		Date date = new Date();
		Object param = new Timestamp(date.getTime()); 
		try(PreparedStatement ps = connection.prepareStatement(sql);){
			ps.setString(1, p.getName());
			ps.setObject(2,param);
			ps.setInt(3, u.getId());
			ps.executeUpdate();
		}
		catch(SQLException e) {
			System.out.println("DB error: " + e.getMessage());
		}
	}
	
	//Add a video to a playlist and save it in DB
	public void addVideoToPlaylist(User u, String videoName, String playlistName) {
		Playlist p = getPLaylistByUserAndName(u, playlistName);
		Video v = VideoDao.getInstance().getVideoByUserAndName(u, videoName);
		saveVideoInPlaylistInDB(p, v);
	}
	
	//TODO what is it for? we can have to search by name and we can have one extra by username
	public Playlist getPLaylistByUserAndName(User u, String name) {
		String sql = "SELECT id FROM playlist WHERE user_id = ? AND name = ?"; 
		try(PreparedStatement ps = connection.prepareStatement(sql);){
			ps.setInt(1, u.getId());
			ps.setString(2, name);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				return new Playlist(rs.getInt(1), name);
			}
		}
		catch(SQLException e){
			System.out.println("DB error: " + e.getMessage());
		}
		return null;
	}
	
	//Save in the database when a video is added in a playlist
	//TODO make it private if its not use anywhere else
	public void saveVideoInPlaylistInDB(Playlist p, Video v) {
		String sql = "INSERT INTO playlist_has_video(playlist_id, video_id) VALUES(?,?)";
		try(PreparedStatement ps = connection.prepareStatement(sql);){
			ps.setInt(1, p.getId());
			ps.setInt(2, v.getId());
			ps.executeUpdate();
		}
		catch(SQLException e) {
			System.out.println("DB error: " + e.getMessage());
		}
	}
}
