package be.kdg.programming5.repository.posts;

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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@SpringBootTest
@TestInstance (TestInstance.Lifecycle.PER_CLASS)
class PostRepositoryTest {
	@Autowired
	private PostRepository postRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ChannelRepository channelRepository;
	private Post post;

	@BeforeEach
	void setUp() {
		final User user = new User("user", LocalDate.of(1992, 11, 19), Role.ADMIN, "password");
		userRepository.save(user);
		final Channel channel = new Channel("channel", "channel description");
		channelRepository.save(channel);
		post = new Post(user, channel, "Cool content", 5, LocalDateTime.of(2023, 3, 23, 12, 0));
		postRepository.save(post);
	}

	@AfterEach
	void tearDown() {
		userRepository.deleteAll();
		postRepository.deleteAll();
		channelRepository.deleteAll();
	}

	@Test
	void setPostUpvoteCountShouldSetUpvotesToSpecifiedValue() {
		final Optional<Post> optionalPostOriginal = postRepository.findById(post.getPostID());
		final Post postOriginal = Assertions.assertDoesNotThrow(optionalPostOriginal::get);
		Assertions.assertEquals(5, postOriginal.getUpVotes());

		postRepository.updateUpVotesByPostID(10, postOriginal.getPostID());

		final Optional<Post> optionalPost = postRepository.findById(post.getPostID());
		final Post post = Assertions.assertDoesNotThrow(optionalPost::get);
		Assertions.assertEquals(10, post.getUpVotes());
	}

	@Test
	void settingUpvotesOfNonExistingPostShouldReturnZeroModifiedRows() {
		Assertions.assertEquals(0, postRepository.updateUpVotesByPostID(10, 999999L));
	}
}
