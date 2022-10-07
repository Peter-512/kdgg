package be.kdg.programming3.repository;

import be.kdg.programming3.domain.Channel;
import be.kdg.programming3.domain.Post;
import be.kdg.programming3.domain.Role;
import be.kdg.programming3.domain.User;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@Component
public class FakeChatRepository implements ChatRepository {
	private static final int INITIAL_CHANNELS = 5;
	private static final int INITIAL_USERS = 25;
	private static final int MAX_INITIAL_POSTS = 100;
	private static final int MIN_INITIAL_POSTS = 20;
	public List<User> users = new ArrayList<>();
	public List<Channel> channels = new ArrayList<>();

	@Autowired
	public FakeChatRepository() {
		seed();
	}

	public void seed() {
		Random random = new Random();
		Faker faker = new Faker();
		channels.addAll(Stream.generate(() -> new Channel(faker.starTrek().character(), faker.yoda().quote()))
		                      .limit(INITIAL_CHANNELS)
		                      .toList());

		users.addAll(Stream.generate(() -> {
			final User user = new User(faker.name().username(), LocalDate.ofInstant(faker.date()
			                                                                             .birthday()
			                                                                             .toInstant(), ZoneId.systemDefault()), Role.randomRole());
			final Channel channel = channels.get(random.nextInt(channels.size()));
			user.joinChannel(channel);
			return user;
		}).limit(INITIAL_USERS).toList());

		channels.forEach(channel -> {
			final List<User> userList = channel.getUsers();
			List<Post> posts = Stream.generate(() -> new Post(userList.get(random.nextInt(userList.size())), channel, faker.lorem()
			                                                                                                               .sentence()))
			                         .limit(faker.number().numberBetween(MIN_INITIAL_POSTS, MAX_INITIAL_POSTS))
			                         .toList();
			posts.forEach(post -> post.setUpVotes(faker.number().numberBetween(-10, 100)));
			channel.getPosts().addAll(posts);
		});
	}

	@Override
	public Channel createChannel(Channel channel) {
		channels.add(channel);
		return channel;
	}

	@Override
	public List<Channel> readChannels() {
		return channels;
	}

	@Override
	public User createUser(User user) {
		users.add(user);
		return user;
	}

	@Override
	public List<User> readUsers() {
		return users;
	}

	@Override
	public boolean deleteUser(User user) {
		return users.removeIf(u -> u == user);
	}

	@Override
	public boolean deleteChannel(Channel channel) {
		return true;
	}

	@Override
	public boolean deletePost(Post post) {
		return true;
	}
}
