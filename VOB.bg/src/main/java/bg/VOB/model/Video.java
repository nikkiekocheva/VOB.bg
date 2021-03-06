package bg.VOB.model;

import java.time.LocalDateTime;

import org.omg.CORBA.OMGVMCID;

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
	private String path;
	private int userId;
	private String imagePath;

	public Video(String name, String description) throws InvalidUserDataException {
		this.name = name;
		if (Validator.verifyVideoDescription(description)) {
			this.description = description;
		} else {
			throw new InvalidUserDataException("Empty video description.");
		}
	}

	public Video(int id, String name, LocalDateTime date, int userId, int views, String description, String path, String imagePath) {
		this.id = id;
		this.name = name;
		this.date = date;
		this.views = views;
		this.userId = userId;
		this.description = description;
		this.path = path;
		this.imagePath = imagePath;
	}
	
	public Video(int id, String name, LocalDateTime date, int userId, int views,int likes, String description, String path,String imagePath) {
		this(id,name,date,userId,views,description,path,imagePath);
		this.likes = likes;
	}

	public Video(int id, String name) {
		this.name = name;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	// can be edited
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

	public String getPath() {
		return path;
	}

	public int getUserId() {
		return userId;
	}

	public String getImagePath() {
		return imagePath;
	}
	
	
}
