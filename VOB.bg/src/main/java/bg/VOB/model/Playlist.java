package bg.VOB.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Playlist {
	
	private int id;
	private String name;
	private LocalDateTime date;
	private int userId;
	
	public Playlist(String name) {
		if(name.length() <= 45) {
			this.name = name;
		}else {
			this.name = "NameTooLong";
		}
		
	}

	public Playlist(int id, String name) {
		this(name);
		this.id = id;
	}
	
	public Playlist(int id, String name,LocalDateTime date,int userId) {
		this(id,name);
		this.date = date;
		this.userId = userId;
	}
	

	public String getName() {
		return name;
	}
	
	//name can be changed in editing the playlist
	public void setName(String name) {
		this.name = name;
	}

	public int getUserId() {
		return userId;
	}

	public int getId() {
		return id;
	}

	public LocalDateTime getDate() {
		return date;
	}

	
	
	
	
}
