package bg.VOB.model;

import java.time.LocalDateTime;

import util.exceptions.InvalidUserDataException;
import util.validation.Validator;

public class Comment {
	
	private int id;
	private LocalDateTime date;
	private int likes;
	private int dislikes;
	private String content;
	private final static int DEFAULT_LIKES = 0;
	private final static int DEFAULT_DISLIKES = 0;
	
	public Comment(String content) throws InvalidUserDataException {
		setContent(content);
		this.likes = DEFAULT_LIKES;
		this.dislikes = DEFAULT_DISLIKES;
	}

	public Comment(int id, String content) throws InvalidUserDataException {
		this(content);
		this.id = id;
	}
	
	public int getId() {
		return id;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public int getLikes() {
		return likes;
	}

	public int getDislikes() {
		return dislikes;
	}
	
	private void setContent(String content) throws InvalidUserDataException{
		if(Validator.verifyCommentContent(content)) {
			this.content = content;
		}
		else {
			throw new InvalidUserDataException("Empty comment!");
		}
	}
	
	public String getContent() {
		return content;
	}
	
	
}
