package be.kdg.programming3.service;

import be.kdg.programming3.domain.Role;
import be.kdg.programming3.domain.User;
import be.kdg.programming3.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public List<User> getUsers() {
		return userRepository.readUsers();
	}

	@Override
	public User getUser(String username) {
		return getUsers().stream().filter(user -> Objects.equals(user.getName(), username)).toList().get(0);
	}

	@Override
	public User addUser(String name, LocalDate birthdate, Role role) {
		return userRepository.createUser(new User(name, birthdate, role));
	}
}
