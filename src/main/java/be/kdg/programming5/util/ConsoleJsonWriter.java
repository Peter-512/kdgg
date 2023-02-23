package be.kdg.programming5.util;

import be.kdg.programming5.model.Channel;
import be.kdg.programming5.model.Post;
import be.kdg.programming5.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private final Logger logger;
	private final Gson gson;

	@Autowired
	public ConsoleJsonWriter() {
		gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
		                        .setPrettyPrinting()
		                        .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
		                        .create();
		this.logger = LoggerFactory.getLogger(ConsoleJsonWriter.class);
	}

	@Override
	public void writePosts(List<Post> posts) throws RuntimeException {
		String json = gson.toJson(posts);
		try (FileWriter writer = new FileWriter(POSTS_JSON)) {
			writer.write(json);
			logger.info("Writing posts to JSON...");
		} catch (IOException e) {
			throw new RuntimeException("Unable to save posts to JSON", e);
		}
	}

	@Override
	public void writeUsers(List<User> users) throws RuntimeException {
		String json = gson.toJson(users);
		try (FileWriter writer = new FileWriter(USERS_JSON)) {
			writer.write(json);
			logger.info("Writing users to JSON...");
		} catch (IOException e) {
			throw new RuntimeException("Unable to save users to JSON", e);
		}
	}

	@Override
	public void writeChannels(List<Channel> channels) throws RuntimeException {
		String json = gson.toJson(channels);
		try (FileWriter writer = new FileWriter(USERS_JSON)) {
			writer.write(json);
			logger.info("Writing channels to JSON...");
		} catch (IOException e) {
			throw new RuntimeException("Unable to save channels to JSON", e);
		}
	}

	public <T> byte[] getJsonBytes(List<T> list) {
		logger.info("Converting to JSON...");
		return gson.toJson(list).getBytes();
	}
}
