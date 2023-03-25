package be.kdg.programming5.repository.users;

import be.kdg.programming5.model.Channel;
import be.kdg.programming5.model.Post;
import be.kdg.programming5.model.Role;
import be.kdg.programming5.model.User;
import be.kdg.programming5.repository.ChannelRepository;
import be.kdg.programming5.repository.PostRepository;
import be.kdg.programming5.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@TestInstance (TestInstance.Lifecycle.PER_CLASS)
class UserRepositoryTest {
	@Autowired
	private PostRepository postRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ChannelRepository channelRepository;

	@BeforeEach
	void setUp() {
		final User u1 = new User("user", LocalDate.of(1992, 11, 19), Role.ADMIN, "password");
		final User u2 = new User("user 2", LocalDate.of(1998, 3, 19), Role.MOD, "password");
		userRepository.saveAll(List.of(u1, u2));
		final Channel c1 = new Channel("channel", "channel description");
		final Channel c2 = new Channel("channel 2", "second channel description");
		channelRepository.saveAll(List.of(c1, c2));
		final Post p1 = new Post(u1, c1, "Cool content", 5, LocalDateTime.of(2023, 3, 23, 12, 0));
		final Post p2 = new Post(u1, c1, "Snazzy content", 6, LocalDateTime.of(2023, 3, 23, 12, 1));
		final Post p3 = new Post(u1, c1, "Awesome content", 7, LocalDateTime.of(2023, 3, 23, 12, 2));
		final Post p4 = new Post(u1, c1, "Boring content", 8, LocalDateTime.of(2023, 3, 23, 12, 3));
		postRepository.saveAll(List.of(p1, p2, p3, p4));
	}

	@AfterEach
	void tearDown() {
		userRepository.deleteAll();
		postRepository.deleteAll();
		channelRepository.deleteAll();
	}

	@Test
	@Transactional
	void deletingUserShouldAlsoDeleteAllTheirPosts() {
		Assertions.assertEquals(4, postRepository.findAll().size());

		userRepository.deleteByName("user");

		final User user = userRepository.findUserByName("user");
		Assertions.assertNull(user);
		Assertions.assertEquals(0, postRepository.findAll().size());
	}

	@Test
	@Transactional
	void deletingNonExistingUserShouldReturnZeroRowsDeleted() {
		Assertions.assertEquals(2, userRepository.findAll().size());

		Assertions.assertEquals(0, userRepository.deleteByName("non-existing user"));

		Assertions.assertEquals(2, userRepository.findAll().size());
		Assertions.assertEquals(4, postRepository.findAll().size());
	}

	@Test
	void userJoiningChannelIsUnique() {
		final Channel channel = channelRepository.findChannelWithUsersByName("channel");
		final User user = userRepository.findUserWithChannelsByName("user");
		Assertions.assertEquals(0, user.getChannels().size());
		Assertions.assertEquals(0, channel.getUsers().size());

		user.joinChannel(channel);
		userRepository.save(user);

		final Channel channelNew = channelRepository.findChannelWithUsersByName("channel");
		final User userNew = userRepository.findUserWithChannelsByName("user");
		Assertions.assertEquals(1, userNew.getChannels().size());
		Assertions.assertEquals(1, channelNew.getUsers().size());

		userNew.joinChannel(channelNew);
		Assertions.assertThrows(DataIntegrityViolationException.class, () -> userRepository.save(user));

		Assertions.assertEquals(1, userRepository.findUserWithChannelsByName("user").getChannels().size());
		Assertions.assertEquals(1, channelRepository.findChannelWithUsersByName("channel").getUsers().size());
	}
}
