package be.kdg.programming3.repository.users;

import be.kdg.programming3.domain.User;

import java.util.List;

public interface UserRepository {
	List<User> findAll();

	User createUser(User user);

	void updateUser(User user);

	boolean deleteUser(User user);
}
