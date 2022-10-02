package be.kdg.programming3.json;

import be.kdg.programming3.domain.Post;
import be.kdg.programming3.domain.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Component
public class ConsoleJsonWriter implements JsonWriter {
	private static final String BASE_PATH = "src/main/resources/";
	private static final String POSTS_JSON = BASE_PATH + "posts.json";
	private static final String USERS_JSON = BASE_PATH + "users.json";
	private final Gson gson;

	@Autowired
	public ConsoleJsonWriter() {
		gson = new GsonBuilder().setPrettyPrinting()
		                        .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
		                        .create();
	}

	@Override
	public void writePosts(List<Post> posts) throws RuntimeException {
		String json = gson.toJson(posts);
		try (FileWriter writer = new FileWriter(POSTS_JSON)) {
			writer.write(json);
			System.out.println("Writing to JSON...");
		} catch (IOException e) {
			throw new RuntimeException("Unable to save posts to JSON", e);
		}
	}

	@Override
	public void writeUsers(List<User> users) throws RuntimeException {
		String json = gson.toJson(users);
		try (FileWriter writer = new FileWriter(USERS_JSON)) {
			writer.write(json);
			System.out.println("Writing to JSON...");
		} catch (IOException e) {
			throw new RuntimeException("Unable to save users to JSON", e);
		}
	}
}
