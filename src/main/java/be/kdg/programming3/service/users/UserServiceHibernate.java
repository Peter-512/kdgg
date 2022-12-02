package be.kdg.programming3.service.users;

import be.kdg.programming3.domain.Role;
import be.kdg.programming3.domain.User;
import be.kdg.programming3.repository.users.UserRepositoryHibernate;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Profile ("prod")
public class UserServiceHibernate implements UserService {
	private final UserRepositoryHibernate userRepository;

	@Autowired
	public UserServiceHibernate(UserRepositoryHibernate userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public List<User> getUsers() {
		return Lists.newArrayList(userRepository.findAll());
	}

	@Override
	public Optional<User> getUser(Long id) {
		return userRepository.findById(id);
	}

	@Override
	public User addUser(String name, LocalDate birthdate, Role role) {
		return userRepository.save(new User(name, birthdate, role));
	}

	@Override
	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}
}
