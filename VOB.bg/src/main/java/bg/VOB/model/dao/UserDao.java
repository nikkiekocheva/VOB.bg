package bg.VOB.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.mindrot.jbcrypt.BCrypt;

import bg.VOB.controller.DBManager;
import bg.VOB.model.User;
import util.exceptions.InvalidUserDataException;

public class UserDao implements IUserDao {

	private static UserDao instance;
	private Connection connection;

	private UserDao() {
		connection = DBManager.getInstance().getConnection();
	}

	public synchronized static UserDao getInstance() {
		if (instance == null) {
			instance = new UserDao();
		}
		return instance;
	}

	@Override
	public boolean checkForUser(String username, String password) throws InvalidUserDataException,SQLException {
		String sql = "SELECT user_name, password FROM users WHERE user_name = ?;";
		try (PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				if (BCrypt.checkpw(password, rs.getString(2))) {
					return true;
				}
			} else {
				throw new InvalidUserDataException("invalid username or password");
			}
		}
		return false;
	}

	@Override
	public User generateUser(String username) throws Exception{
		User u = null;
		String sql = "SELECT id, user_name, password, email, phone_number, age FROM users WHERE user_name = ?";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				u = new User(rs.getInt("id"), rs.getString("user_name"), rs.getString("password"),
						rs.getString("email"), rs.getString("phone_number"), rs.getInt("age"));
			}
		}
		
		return u;
	}

	@Override
	public User generateUserById(int id) throws Exception{
		User u = null;
		String sql = "SELECT id, user_name, password, email, phone_number, age FROM users WHERE id = ?";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				u = new User(rs.getInt("id"), rs.getString("user_name"), rs.getString("password"),
						rs.getString("email"), rs.getString("phone_number"), rs.getInt("age"));
			}
		} 
		if(u == null) {
			throw new Exception("Cant get user by ID!");
		}
		return u;
	}

	@Override
	public void saveUserInDB(User u) throws SQLException {
		try (PreparedStatement ps = connection
				.prepareStatement("INSERT INTO users(user_name,password,email,phone_number,age) VALUES (?,?,?,?,?)");) {
			ps.setString(1, u.getUsername());
			ps.setString(2, u.getPassword());
			ps.setString(3, u.getEmail());
			ps.setString(4, u.getPhoneNumber());
			ps.setInt(5, u.getAge());
			ps.executeUpdate();
		} 
	}

	@Override
	public void followUser(User follower, User following) throws SQLException {
		try (PreparedStatement ps = connection
				.prepareStatement("INSERT INTO follower_following(follower_id,following_id) VALUES (?,?)")) {
			ps.setInt(1, follower.getId());
			ps.setInt(2, following.getId());
			ps.executeUpdate();
		} 
	}
	
	@Override
	public void unFollowUser(User follower, User following) throws SQLException {
		try (PreparedStatement ps = connection
				.prepareStatement("DELETE FROM follower_following WHERE follower_id = ? AND following_id =?")) {
			ps.setInt(1, follower.getId());
			ps.setInt(2, following.getId());
			ps.executeUpdate();
		}
	}
	
	@Override
	public boolean checkIfUserIsFollowingAnotherUser(User follower, User following) throws SQLException {
		try (PreparedStatement ps = connection
				.prepareStatement("SELECT follower_id,following_id FROM follower_following WHERE follower_id = ? AND following_id = ?")) {
			ps.setInt(1, follower.getId());
			ps.setInt(2, following.getId());
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				return true;
			}
		} 
		return false;
	}
	
	@Override
	public void updateUserInDB(User u)  throws SQLException{
		String sql = "UPDATE users SET user_name = ? ,password = ?, email = ?,phone_number = ? WHERE id = ?";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, u.getUsername());
			ps.setString(2, u.getPassword());
			ps.setString(3, u.getEmail());
			ps.setString(4, u.getPhoneNumber());
			ps.setInt(5, u.getId());
			ps.executeUpdate();
		}
	}

	@Override
	public ArrayList<User> searchForUser(String text) throws SQLException{
		ArrayList<User> matches = new ArrayList<>();
		String sql = "SELECT id, user_name, email, phone_number, age FROM users WHERE user_name LIKE ?";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, "%" + text + "%");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				matches.add(new User(rs.getInt("id"), rs.getString("user_name"), rs.getString("email"),
						rs.getString("phone_number"), rs.getInt("age")));
			}
		}
		return matches;
	}

	public ArrayList<User> getAllFollowingUsers(User follower) throws SQLException{
		ArrayList<User> followingUsers = new ArrayList<>();
		String sql = "SELECT u.id, u.user_name, u.email,u.phone_number, u.age FROM users AS u" + 
				" JOIN follower_following AS f ON u.id = f.following_id WHERE f.follower_id = ?";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, follower.getId());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				followingUsers.add(new User(rs.getInt("id"), rs.getString("user_name"), rs.getString("email"),
						rs.getString("phone_number"), rs.getInt("age")));
			}
		}
		return followingUsers;
	}
	
}
