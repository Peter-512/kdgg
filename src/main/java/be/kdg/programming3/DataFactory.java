package be.kdg.programming3;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
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

		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setPrettyPrinting();
		Gson gson = new GsonBuilder()
				.setPrettyPrinting()
				.registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
				.create();
		String channelsJson = gson.toJson(DataFactory.channels);
		String usersJson = gson.toJson(DataFactory.users);
		String postsJson = gson.toJson(DataFactory.posts);


		//		Saving to JSON
		try (FileWriter jsonWriter = new FileWriter("src/main/resources/channels.json")) {
			jsonWriter.write(channelsJson);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try (FileWriter jsonWriter = new FileWriter("src/main/resources/users.json")) {
			jsonWriter.write(usersJson);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try (FileWriter jsonWriter = new FileWriter("src/main/resources/posts.json")) {
			jsonWriter.write(postsJson);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
