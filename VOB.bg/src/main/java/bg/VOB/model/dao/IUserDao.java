package bg.VOB.model.dao;

import java.util.ArrayList;

import bg.VOB.model.User;
import util.exceptions.InvalidUserDataException;

public interface IUserDao {

	boolean checkForUser(String username, String password) throws InvalidUserDataException;

	void saveUserInDB(User u);

	User generateUser(String username);

	void followUser(User follower, User following);

	void updateUserInDB(User u);

	User generateUserById(int id);

	ArrayList<User> searchForUser(String text);
}
