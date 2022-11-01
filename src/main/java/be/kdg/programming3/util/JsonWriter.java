package be.kdg.programming3.util;

import be.kdg.programming3.domain.Post;
import be.kdg.programming3.domain.User;

import java.util.List;

public interface JsonWriter {
	void writePosts(List<Post> posts) throws RuntimeException;

	void writeUsers(List<User> users) throws RuntimeException;
}
