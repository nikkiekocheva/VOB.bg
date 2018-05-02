package bg.VOB.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	public void addComment(User u, String videoName, String content) throws InvalidUserDataException {
		Video v = VideoDao.getInstance().getVideoByName(videoName);
		if (Validator.verifyCommentContent(content)) {
			String sql = "INSERT INTO comments(date, video_id, user_id, content) VALUES(?,?,?,?)";
			Date date = new Date();
			Object param = new java.sql.Timestamp(date.getTime());
			try (PreparedStatement ps = connection.prepareStatement(sql);) {
				ps.setObject(1, param);
				ps.setInt(2, v.getId());
				ps.setInt(3, u.getId());
				ps.setString(4, content);
				ps.executeUpdate();
			} catch (SQLException e) {
				System.out.println("DB error: " + e.getMessage());
			}
		} else {
			throw new InvalidUserDataException("Empty comment");
		}

	}

	@Override
	public void editComment(User u, String videoName, int commentId, String content) throws InvalidUserDataException {
		Video v = VideoDao.getInstance().getVideoByName(videoName);
		String sql = "UPDATE comments SET content = ? WHERE video_id = ? AND id = ?";
		if (Validator.verifyCommentContent(content)) {
			try (PreparedStatement ps = connection.prepareStatement(sql);) {
				ps.setString(1, content);
				ps.setInt(2, v.getId());
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
	public void deleteComment(User u, String videoName, int commentId) {
		Video v = VideoDao.getInstance().getVideoByName(videoName);
		String sql = "DELETE FROM comments WHERE video_id = ? AND id = ?";
		try (PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, v.getId());
			ps.setInt(2, commentId);
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("DB error: " + e.getMessage());
		}
	}

	@Override
	public void likeComment(User u, Comment comment) {
		String sql;
		// see if the comment is all ready liked or disliked by the user
		if (!isCommentLikedByUser(u, comment)) {
			sql = "INSERT INTO comment_like_dislike(user_id, comments_id, liked) VALUES (?,?,?)";
			try (PreparedStatement ps = connection.prepareStatement(sql);) {
				ps.setInt(1, u.getId());
				ps.setInt(2, comment.getId());
				ps.setInt(3, 1);
				ps.executeUpdate();

			} catch (SQLException e) {
				System.out.println("DB error: " + e.getMessage());
			}
			// if it is allready liked unlike it
		} else {
			sql = "UPDATE video_like_dislike SET liked_disliked = 0 WHERE user_id = ? AND video_id = ?";
			try (PreparedStatement ps = connection.prepareStatement(sql);) {
				ps.setInt(1, u.getId());
				ps.setInt(2, comment.getId());
				ps.executeUpdate();
			} catch (SQLException e) {
				System.out.println("DB error: " + e.getMessage());
			}
		}
	}

	// Check if the user has allready liked the comment
	private boolean isCommentLikedByUser(User u, Comment c) {
		String sql = "SELECT like FROM comment_like_dislike WHERE user_id = ? AND comment_id = ?";
		try (PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, u.getId());
			ps.setInt(2, c.getId());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			System.out.println("DB error: " + e.getMessage());
		}
		return false;
	}

	public ArrayList<Comment> getAllComments(int videoId) throws SQLException {
		ArrayList<Comment> allComments = new ArrayList<>();
		String sql = "SELECT id, date, user_id, content FROM comments WHERE video_id = ? ORDER BY date";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, videoId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Comment c = new Comment(rs.getInt("id"), rs.getTimestamp("date").toLocalDateTime(),
						rs.getInt("user_id"), rs.getString("content"));
				c.setUsername(getUsername(c.getUserId()));
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				 String formatDateTime = c.getDate().format(formatter);
				 c.setFormattedDate(formatDateTime);
				allComments.add(c);
			}
		} catch (SQLException e) {
			System.out.println("DB error: " + e.getMessage());
		}

		return allComments;
	}
	
	private String getUsername(int userId) {
		String sql = "SELECT user_name FROM users WHERE id = ?";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				return rs.getString("user_name");
			}
		} catch (SQLException e) {
			System.out.println("DB error: " + e.getMessage());
		}
		return null;
	}

}
