package be.kdg.programming5.service.posts;

import be.kdg.programming5.exceptions.PostNotFoundException;
import be.kdg.programming5.model.Channel;
import be.kdg.programming5.model.Post;
import be.kdg.programming5.model.Role;
import be.kdg.programming5.model.User;
import be.kdg.programming5.service.channels.ChannelService;
import be.kdg.programming5.service.users.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
@TestInstance (TestInstance.Lifecycle.PER_CLASS)
class PostServiceImplTest {
	@Autowired
	private PostService postService;
	@Autowired
	private UserService userService;
	@Autowired
	private ChannelService channelService;

	private Post post;

	@BeforeEach
	void setUp() {
		final User user = userService.addUser("user", LocalDate.of(1992, 11, 19), Role.ADMIN, "password");
		final Channel channel = channelService.addChannel("channel", "channel description");
		post = postService.addPost(user, channel, "Cool content");
	}

	@AfterEach
	void tearDown() {
		userService.getUsers().stream().map(User::getUserID).forEach(userService::deleteUser);
		channelService.getChannels().stream().map(Channel::getChannelID).forEach(channelService::deleteChannel);
		postService.getPosts().stream().map(Post::getPostID).forEach(postService::deletePost);
	}

	@Test
	void updatePostShouldChangeContentOfExistingPost() {
		final String newContent = "New content";
		final Post updatedPost = Assertions.assertDoesNotThrow(() -> postService.updatePost(post.getPostID(), newContent));

		Assertions.assertEquals(newContent, updatedPost.getContent());
	}

	@Test
	void updatingNonExistingPostShouldThrowException() {
		Assertions.assertThrows(PostNotFoundException.class, () -> postService.updatePost(999999L, "blabla"));
	}
}
