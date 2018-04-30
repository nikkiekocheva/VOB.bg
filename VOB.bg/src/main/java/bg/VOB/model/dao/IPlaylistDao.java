package bg.VOB.model.dao;

import bg.VOB.model.Playlist;
import bg.VOB.model.User;
import bg.VOB.model.Video;

public interface IPlaylistDao {

	public void savePlaylistInDB(User u, Playlist p);
	public void addVideoToPlaylist(User u, String videoName, String playlistName);
	public Playlist getPLaylistByUserAndName(User u, String name);
	public void saveVideoInPlaylistInDB(Playlist p, int videoId);
	
}
