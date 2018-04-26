package bg.VOB.model;

import java.time.LocalDateTime;

import util.exceptions.InvalidUserDataException;
import util.validation.Validator;


public class Video {
	
	private int id;
	private String name;
	private LocalDateTime date;
	private int views;
	private int likes;
	private int dislikes;
	private String description;
	
	public Video(String name, String description) throws InvalidUserDataException {
		this.name = name;
		if(Validator.verifyVideoDescription(description)) {
			this.description = description;
		}
		else {
			throw new InvalidUserDataException("Empty video description.");
		}
	}

	public Video(int id, String name) {
		this.name = name;
		this.id = id;
	}
	public String getName() {
		return name;
	}

	//can be edited
	public void setName(String name) {
		this.name = name;
	}

	public int getLikes() {
		return likes;
	}

	public int getDislikes() {
		return dislikes;
	}

	public int getId() {
		return id;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public int getViews() {
		return views;
	}

	public String getDescription() {
		return description;
	}
	
	

}
