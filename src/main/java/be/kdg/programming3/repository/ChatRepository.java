package be.kdg.programming3.repository;

import be.kdg.programming3.domain.Channel;
import be.kdg.programming3.domain.Post;
import be.kdg.programming3.domain.User;

import java.util.List;

public interface ChatRepository {
	Channel createChannel(Channel channel);

	List<Channel> readChannels();

	User createUser(User user);

	List<User> readUsers();

	boolean deleteUser(User user);

	Channel deleteChannel(Channel channel);

	Post deletePost(Post post);
}
