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
	private String username;

	public Comment(String content) throws InvalidUserDataException {
		setContent(content);
	}

	public Comment(int id, String content) throws InvalidUserDataException {
		this(content);
		this.id = id;
	}
	
	public Comment(int id, LocalDateTime date, int userId, String content) {
		this.id = id;
		this.date = date;
		this.content = content;
		this.userId = userId;
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
	

}
