package be.kdg.programming3.repository.users;

import be.kdg.programming3.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
	List<User> findAll();

	User createUser(User user);

	void updateUser(User user);

	boolean deleteUser(User user);

	Optional<User> findById(Long id);

	long countByUserID(Long userID);
}
