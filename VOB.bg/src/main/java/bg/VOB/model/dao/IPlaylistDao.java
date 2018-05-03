package bg.VOB.model.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import bg.VOB.model.Playlist;
import bg.VOB.model.User;
import bg.VOB.model.Video;

public interface IPlaylistDao {

	public Playlist getPLaylistByUserAndName(User u, String name) throws SQLException;

	public void saveVideoInPlaylistInDB(Playlist p, int videoId) throws SQLException;

	Playlist addPlaylist(User u, String name) throws SQLException;

	Playlist getPLaylistByUser(User u) throws SQLException;

	boolean checkIfVideoIsInPlaylist(Playlist p, int videoId) throws SQLException;

	ArrayList<Video> getVideosFromPlaylist(User u) throws SQLException;

	ArrayList<Playlist> searchForPlaylist(String text) throws SQLException;
	
	void removeVideoFromPlaylistInDB(Playlist p, int videoId) throws SQLException;

}
