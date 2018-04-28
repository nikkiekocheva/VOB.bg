package bg.VOB.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

import bg.VOB.controller.DBManager;
import bg.VOB.model.User;
import util.exceptions.InvalidUserDataException;

public class UserDao implements IUserDao{
	
	private static UserDao instance;
	private Connection connection;
	
	private UserDao() {
		connection = DBManager.getInstance().getConnection();
	}
	
	public synchronized static UserDao getInstance() {
		if(instance == null) {
			instance = new UserDao();
		}
		return instance;
	}

	@Override
	public boolean checkForUser(String username, String password) throws InvalidUserDataException{
		try (PreparedStatement ps = connection.prepareStatement("SELECT user_name, password FROM users WHERE user_name = ?;");) {
		ps.setString(1, username);
		ResultSet rs = ps.executeQuery();
		
		if(rs.next()) {
			if(BCrypt.checkpw(password, rs.getString(2))) {
				System.out.println("true");
				return true;
			}
		}else {
			throw new InvalidUserDataException("invalid username or password");
		}
		
		} catch (InvalidUserDataException e) {
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
	
	public User generateUser(String username) {
		User u = null;
		String sql = "SELECT id, user_name, password, email, phone_number, age FROM users WHERE user_name = ?";
		try (PreparedStatement ps = connection
				.prepareStatement(sql)) {
		ps.setString(1, username);
		ResultSet rs = ps.executeQuery();
	
		while(rs.next()) {
			u = new User(rs.getInt("id"),rs.getString("user_name"), rs.getString("password"), rs.getString("email"),
						rs.getString("phone_number"), rs.getInt("age"));
		}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return u;
	}

	@Override
	public void saveUserInDB(User u) {
		System.out.println("going to save db");
		try (PreparedStatement ps = connection
				.prepareStatement("INSERT INTO users(user_name,password,email,phone_number,age) VALUES (?,?,?,?,?)");) {
			ps.setString(1, u.getUsername());
			ps.setString(2, u.getPassword());
			ps.setString(3, u.getEmail());
			ps.setString(4, u.getPhoneNumber());
			ps.setInt(5, u.getAge());
			ps.executeUpdate();
			System.out.println("done");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void followUser(User follower, User following) {
		//TODO check if the user is followed by that user 
		try(PreparedStatement ps = connection.prepareStatement("INSERT INTO follower_following(follower_id,following_id) VALUES (?,?)")){
			ps.setInt(1, follower.getId());
			ps.setInt(2, following.getId());
			ps.executeUpdate();
		}catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
	}

	@Override
	public void updateUserInDB(User u) {
		String sql = "UPDATE users SET user_name = ? ,password = ?, email = ?,phone_number = ? WHERE id = ?";
		try(PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setString(1, u.getUsername());
			ps.setString(2, u.getPassword());
			ps.setString(3, u.getEmail());
			ps.setString(4, u.getPhoneNumber());
			ps.setInt(5, u.getId());
			ps.executeUpdate();
		}catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
}
