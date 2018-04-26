package bg.VOB.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Playlist {
	
	private int id;
	private String name;
	private LocalDateTime date;
	
	public Playlist(String name) {
		this.name = name;
	}

	public Playlist(int id, String name) {
		this.name = name;
		this.id = id;
	}

	public String getName() {
		return name;
	}
	
	//name can be changed in editing the playlist
	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public LocalDateTime getDate() {
		return date;
	}

	
	
	
	
}
