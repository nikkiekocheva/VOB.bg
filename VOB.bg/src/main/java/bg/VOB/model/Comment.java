package bg.VOB.model;

import java.time.LocalDateTime;

import util.exceptions.InvalidUserDataException;
import util.validation.Validator;

public class Comment {

	private int id;
	private LocalDateTime date;
	private String content;
	private String formattedDate;
	private int userId;
	private int videoId;
	private String username;
	private int likes;
	private int dislikes;
	
	public Comment(String content) throws InvalidUserDataException {
		setContent(content);
	}

	public Comment(int id, String content) throws InvalidUserDataException {
		this(content);
		this.id = id;
	}
	
	public Comment(int id, LocalDateTime date, int userId,int videoId, String content) {
		this.id = id;
		this.date = date;
		this.content = content;
		this.userId = userId;
		this.videoId = videoId;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public int getId() {
		return id;
	}

	public LocalDateTime getDate() {
		return date;
	}

	private void setContent(String content) throws InvalidUserDataException {
		if (Validator.verifyCommentContent(content)) {
			this.content = content;
		} else {
			throw new InvalidUserDataException("Empty comment!");
		}
	}

	public String getContent() {
		return content;
	}

	public int getUserId() {
		return userId;
	}
	
	public String getUsername() {
		return username;
	}

	public String getFormattedDate() {
		return formattedDate;
	}

	public void setFormattedDate(String formattedDate) {
		this.formattedDate = formattedDate;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public int getDislikes() {
		return dislikes;
	}

	public void setDislikes(int dislikes) {
		this.dislikes = dislikes;
	}

	public int getVideoId() {
		return videoId;
	}

}
