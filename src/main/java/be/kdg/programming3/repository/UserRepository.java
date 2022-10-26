package be.kdg.programming3.repository;

import be.kdg.programming3.domain.User;

import java.util.List;

public interface UserRepository {
	User createUser(User user);

	List<User> readUsers();

	boolean deleteUser(User user);
}
