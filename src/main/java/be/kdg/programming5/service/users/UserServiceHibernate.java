package be.kdg.programming5.service.users;

import be.kdg.programming5.model.Role;
import be.kdg.programming5.model.User;
import be.kdg.programming5.repository.PostRepository;
import be.kdg.programming5.repository.UserRepository;
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
	private final UserRepository userRepository;
	private final PostRepository postRepository;

	@Autowired
	public UserServiceHibernate(UserRepository userRepository, PostRepository postRepository) {
		this.userRepository = userRepository;
		this.postRepository = postRepository;
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

	@Override
	public long getPostsCountOfUser(long userID) {
		return postRepository.countByUser_UserID(userID);
	}

	@Override
	public User updateUser(Long id, String name, LocalDate birthdate, Role role) {
		return userRepository.findById(id).map(user -> {
			user.setName(name);
			user.setBirthdate(birthdate);
			user.setRole(role);
			return userRepository.save(user);
		}).orElse(null);
	}
}
