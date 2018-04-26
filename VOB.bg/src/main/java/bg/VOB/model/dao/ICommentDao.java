package bg.VOB.model.dao;

import bg.VOB.model.Comment;
import bg.VOB.model.User;
import util.exceptions.InvalidUserDataException;

public interface ICommentDao {

	public void addComment(User u, String videoName, String content) throws InvalidUserDataException;
	
	public void editComment(User u, String videoName, int commentId, String content) throws InvalidUserDataException;
	
	public void deleteComment(User u, String videoName, int commentId);
	
	void likeComment(User u, Comment comment);
	
	
}
