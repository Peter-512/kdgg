package be.kdg.programming3.repository;

import be.kdg.programming3.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {
	private final List<User> users;

	@Autowired
	public UserRepositoryImpl() {
		users = new ArrayList<>();
	}

	@Override
	public User createUser(User user) {
		users.add(user);
		return user;
	}

	@Override
	public List<User> readUsers() {
		return users;
	}

	@Override
	public boolean deleteUser(User user) {
		return users.removeIf(u -> u == user);
	}
}
