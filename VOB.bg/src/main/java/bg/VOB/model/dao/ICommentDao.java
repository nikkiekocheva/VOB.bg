package bg.VOB.model.dao;

import bg.VOB.model.Comment;
import bg.VOB.model.User;
import util.exceptions.InvalidUserDataException;

public interface ICommentDao {

	public void addComment(User u, int videoId, String content) throws InvalidUserDataException;

	public void editComment(User u, int videoId, int commentId, String content) throws InvalidUserDataException;

	public void deleteComment(User u, int videoId, int commentId);

	void likeComment(User u, Comment comment);

}
