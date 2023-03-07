package be.kdg.programming5.service.users;

import be.kdg.programming5.model.Role;
import be.kdg.programming5.model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserService {
	List<User> getUsers();

	Optional<User> getUser(Long id);

	User addUser(String name, LocalDate birthdate, Role role, String password);

	void deleteUser(Long id);

	long getPostsCountOfUser(long userID);

	User updateUser(Long id, String name, LocalDate birthdate, Role role);
}
