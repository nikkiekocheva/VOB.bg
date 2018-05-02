package bg.VOB.model.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import bg.VOB.model.Comment;
import bg.VOB.model.User;
import util.exceptions.InvalidUserDataException;

public interface ICommentDao {

	public void addComment(User u, int videoId, String content) throws InvalidUserDataException;

	public void editComment(User u, int videoId, int commentId, String content) throws InvalidUserDataException;

	public void deleteComment(User u, int videoId, int commentId);

	void likeComment(User u, Comment comment) throws SQLException;

	public void dislikeComment(User u, Comment comment) throws SQLException;
	
	public int getLikedDisliked(User u, Comment c) throws SQLException;
	
	public int getCommentLikes(int id) throws SQLException;
	
	public int getCommentDislikes(int id) throws SQLException;
	
	public ArrayList<Comment> getAllComments(int videoId) throws SQLException;
	
	public Comment generateCommentById(int commentId);
	
}
