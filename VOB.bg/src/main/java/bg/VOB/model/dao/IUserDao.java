package bg.VOB.model.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import bg.VOB.model.User;
import util.exceptions.InvalidUserDataException;

public interface IUserDao {

	boolean checkForUser(String username, String password) throws InvalidUserDataException,SQLException;

	void saveUserInDB(User u) throws SQLException;

	User generateUser(String username) throws Exception;

	void followUser(User follower, User following) throws SQLException;

	void updateUserInDB(User u) throws SQLException;

	User generateUserById(int id) throws Exception;

	ArrayList<User> searchForUser(String text) throws SQLException;
	
	boolean checkIfUserIsFollowingAnotherUser(User follower, User following) throws SQLException;
	
	void unFollowUser(User follower, User following) throws SQLException;
}
