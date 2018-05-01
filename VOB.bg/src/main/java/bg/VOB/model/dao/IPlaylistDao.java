package bg.VOB.model.dao;

import java.util.ArrayList;

import bg.VOB.model.Playlist;
import bg.VOB.model.User;
import bg.VOB.model.Video;

public interface IPlaylistDao {

	public void savePlaylistInDB(User u, Playlist p);

	public void addVideoToPlaylist(User u, String videoName, String playlistName);

	public Playlist getPLaylistByUserAndName(User u, String name);

	public void saveVideoInPlaylistInDB(Playlist p, int videoId);

	Playlist addPlaylist(User u, String name);

	Playlist getPLaylistByUser(User u);

	boolean checkIfVideoIsInPlaylist(Playlist p, int videoId);

	ArrayList<Video> getVideosFromPlaylist(User u);

	ArrayList<Playlist> searchForPlaylist(String text);

}
