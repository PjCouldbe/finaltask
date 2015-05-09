package db.repository;

import java.util.List;

import db.model.User;

public interface UserRepository {
	void addUser(final User user);
	
	boolean update(final User user, int id);
	
	boolean delete(int id);
	
	User selectUser(int id);
	
	List<User> showAllUsers();
	
	int getCount();
}
