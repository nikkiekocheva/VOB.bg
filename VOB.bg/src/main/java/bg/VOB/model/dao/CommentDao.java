package bg.VOB.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import bg.VOB.controller.DBManager;
import bg.VOB.model.Comment;
import bg.VOB.model.User;
import bg.VOB.model.Video;
import util.exceptions.InvalidUserDataException;
import util.validation.Validator;

public class CommentDao implements ICommentDao {

	private static CommentDao instance;
	private Connection connection;

	private CommentDao() {
		connection = DBManager.getInstance().getConnection();
	}

	public synchronized static CommentDao getInstance() {
		if (instance == null) {
			instance = new CommentDao();
		}
		return instance;
	}

	@Override
	public void addComment(User u, int videoId, String content) throws InvalidUserDataException, SQLException {
		if (Validator.verifyCommentContent(content)) {
			String sql = "INSERT INTO comments(date, video_id, user_id, content) VALUES(?,?,?,?)";
			Date date = new Date();
			Object param = new Timestamp(date.getTime());
			try (PreparedStatement ps = connection.prepareStatement(sql);) {
				ps.setObject(1, param);
				ps.setInt(2, videoId);
				ps.setInt(3, u.getId());
				ps.setString(4, content);
				ps.executeUpdate();
			}
		} else {
			throw new InvalidUserDataException("Empty comment");
		}
	}

	@Override
	public void editComment(User u, int videoId, int commentId, String content) throws InvalidUserDataException {
		String sql = "UPDATE comments SET content = ? WHERE video_id = ? AND id = ?";
		if (Validator.verifyCommentContent(content)) {
			try (PreparedStatement ps = connection.prepareStatement(sql);) {
				ps.setString(1, content);
				ps.setInt(2, videoId);
				ps.setInt(3, commentId);
				ps.executeUpdate();
			} catch (SQLException e) {
				System.out.println("DB error: " + e.getMessage());
			}
		} else {
			throw new InvalidUserDataException("Can't edit with an empty text.");
		}
	}

	@Override
	public void deleteComment(User u, int videoId, int commentId) throws SQLException {
		String sql = "DELETE FROM comments WHERE video_id = ? AND id = ?";
		try (PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, videoId);
			ps.setInt(2, commentId);
			ps.executeUpdate();
		}
	}

	@Override
	public void likeComment(User u, Comment comment) throws SQLException {
		String sql;
		// see if the comment is all ready liked or disliked by the user
		if (!isCommentLikedByUser(u, comment)) {
			sql = "INSERT INTO comment_like_dislike(user_id, comment_id, liked_disliked) VALUES (?,?,?)";
			try (PreparedStatement ps = connection.prepareStatement(sql);) {
				ps.setInt(1, u.getId());
				ps.setInt(2, comment.getId());
				ps.setInt(3, 1);
				ps.executeUpdate();
			}
		// if it is allready liked unlike it
		} else {
			int existAs = getLikedDisliked(u, comment);
			if (existAs == 1) {
				sql = "UPDATE comment_like_dislike SET liked_disliked = 0 WHERE user_id = ? AND comment_id = ?";
			} else {
				sql = "UPDATE comment_like_dislike SET liked_disliked = 1 WHERE user_id = ? AND comment_id = ?";
			}
			
			try (PreparedStatement ps = connection.prepareStatement(sql);) {
				ps.setInt(1, u.getId());
				ps.setInt(2, comment.getId());
				ps.executeUpdate();
			}
		}
	}

	@Override
	public void dislikeComment(User u, Comment comment) throws SQLException {
		String sql;
		// see if the comment is all ready liked or disliked by the user
		if (!isCommentLikedByUser(u, comment)) {
			sql = "INSERT INTO comment_like_dislike(user_id, comment_id, liked_disliked) VALUES (?,?,?)";
			try (PreparedStatement ps = connection.prepareStatement(sql);) {
				ps.setInt(1, u.getId());
				ps.setInt(2, comment.getId());
				ps.setInt(3, -1);
				ps.executeUpdate();
			}
		// if it is allready disliked unlike it
		} else {

			int existAs = getLikedDisliked(u, comment);
			if (existAs == -1) {
				sql = "UPDATE comment_like_dislike SET liked_disliked = 0 WHERE user_id = ? AND comment_id = ?";
			} else {
				sql = "UPDATE comment_like_dislike SET liked_disliked = -1 WHERE user_id = ? AND comment_id = ?";
			}
			try (PreparedStatement ps = connection.prepareStatement(sql);) {
				ps.setInt(1, u.getId());
				ps.setInt(2, comment.getId());
				ps.executeUpdate();
			}
		}
	}

	// Check if the user has already liked the comment
	private boolean isCommentLikedByUser(User u, Comment c) throws SQLException {
		String sql = "SELECT liked_disliked FROM comment_like_dislike WHERE user_id = ? AND comment_id = ?";
		try (PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, u.getId());
			ps.setInt(2, c.getId());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int getLikedDisliked(User u, Comment c) throws SQLException {
		String sql = "SELECT liked_disliked FROM comment_like_dislike WHERE user_id = ? AND comment_id = ?";
		try (PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, u.getId());
			ps.setInt(2, c.getId());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		}
		return 0;
	}

	@Override
	public int getCommentLikes(int id) throws SQLException {
		String sql = "SELECT SUM(liked_disliked) AS likes FROM comment_like_dislike WHERE comment_id = ? AND liked_disliked = 1";
		try (PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt("likes");
			}
		}
		return 0;
	}

	@Override
	public int getCommentDislikes(int id) throws SQLException {
		String sql = "SELECT SUM(liked_disliked)*(-1) AS dislikes FROM comment_like_dislike WHERE comment_id = ? AND liked_disliked = -1";
		try (PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt("dislikes");
			}
		}
		return 0;
	}

	@Override
	public ArrayList<Comment> getAllComments(int videoId) throws SQLException {
		ArrayList<Comment> allComments = new ArrayList<>();
		String sql = "SELECT id, date, user_id,video_id, content FROM comments WHERE video_id = ? ORDER BY date";

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, videoId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Comment c = new Comment(rs.getInt("id"), rs.getTimestamp("date").toLocalDateTime(),
										rs.getInt("user_id"), rs.getInt("video_id"), rs.getString("content"));
				//set the date of the comment and who posted it
				c.setUsername(getUsername(c.getUserId()));
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				String formatDateTime = c.getDate().format(formatter);
				c.setFormattedDate(formatDateTime);

				c.setLikes(getCommentLikes(c.getId()));
				c.setDislikes(getCommentDislikes(c.getId()));
				allComments.add(c);
			}
		}

		return allComments;
	}
	
	@Override
	public Comment generateCommentById(int commentId) throws SQLException {
		String sql = "SELECT date, user_id, video_id, content FROM comments WHERE id = ?";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, commentId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Comment c = new Comment(commentId, rs.getTimestamp("date").toLocalDateTime(), rs.getInt("user_id"),
										rs.getInt("video_id"), rs.getString("content"));
				//set the date of the comment and who posted it
				c.setUsername(getUsername(c.getUserId()));
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				String formatDateTime = c.getDate().format(formatter);
				c.setFormattedDate(formatDateTime);
				return c;
			}
		}

		return null;
	}

	private String getUsername(int userId) throws SQLException {
		String sql = "SELECT user_name FROM users WHERE id = ?";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getString("user_name");
			}
		}
		return null;
	}

}
