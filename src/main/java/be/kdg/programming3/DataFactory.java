package be.kdg.programming3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class DataFactory {
	public static List<User> users = new ArrayList<>();
	public static List<Channel> channels = new ArrayList<>();
	public static List<Post> posts = new ArrayList<>();

	public static void seed() {
		Random random = new Random();
		channels.addAll(Stream.generate(Channel::createRandom).limit(5).toList());
		users.addAll(Stream.generate(() -> {
			final User user = User.createRandom();
			final Channel channel = channels.get(random.nextInt(channels.size()));
			channel.addUser(user);
			return user;
		}).limit(25).toList());
		posts.addAll(Stream.generate(() -> {
			User user = users.get(random.nextInt(users.size()));
			Channel channel = channels.get(random.nextInt(channels.size()));
			Post post = Post.createRandom(user, channel);
			user.createPost(channel, post);
			return post;
		}).limit(100).toList());
	}
}
