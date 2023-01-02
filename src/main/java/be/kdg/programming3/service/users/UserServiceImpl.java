package be.kdg.programming3.service.users;

import be.kdg.programming3.domain.Role;
import be.kdg.programming3.domain.User;
import be.kdg.programming3.repository.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Profile ({"list", "dev", "em"})
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public List<User> getUsers() {
		return userRepository.findAll();
	}

	@Override
	public Optional<User> getUser(Long id) {
		final List<User> users = getUsers().stream()
		                                   .filter(user -> Objects.equals(user.getUserID(), id))
		                                   .toList();
		return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
	}

	@Override
	public User addUser(String name, LocalDate birthdate, Role role) {
		return userRepository.createUser(new User(name, birthdate, role));
	}

	@Override
	public void deleteUser(Long id) {
		getUser(id).ifPresent(userRepository::deleteUser);
	}
}
