package be.kdg.programming3.repository.users;

import be.kdg.programming3.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Profile ("list")
public class UserRepositoryList implements UserRepository {
	private static Long idCounter = 0L;
	private final List<User> users;

	@Autowired
	public UserRepositoryList() {
		users = new ArrayList<>();
	}

	@Override
	public List<User> findAll() {
		return users;
	}

	@Override
	public User createUser(User user) {
		user.setUserID(idCounter++);
		users.add(user);
		return user;
	}

	@Override
	public void updateUser(User user) {
		//		TODO
	}

	@Override
	public boolean deleteUser(User user) {
		return users.removeIf(u -> u == user);
	}

	@Override
	public Optional<User> findById(Long id) {
		return users.stream()
		            .filter(user -> user.getUserID().equals(id))
		            .findFirst();
	}

	@Override
	public long countByUserID(Long userID) {
		return findById(userID).orElseThrow().getPosts().size();
	}
}
