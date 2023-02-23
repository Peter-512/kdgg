package be.kdg.programming5.util;

import be.kdg.programming5.model.Channel;
import be.kdg.programming5.model.Post;
import be.kdg.programming5.model.User;

import java.util.List;

public interface JsonWriter {
	void writePosts(List<Post> posts) throws RuntimeException;

	void writeUsers(List<User> users) throws RuntimeException;

	void writeChannels(List<Channel> channels) throws RuntimeException;

	<T> byte[] getJsonBytes(List<T> list);
}
