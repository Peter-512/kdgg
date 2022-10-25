package be.kdg.programming3.service;

import be.kdg.programming3.domain.Channel;
import be.kdg.programming3.domain.Role;
import be.kdg.programming3.domain.User;
import be.kdg.programming3.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class ChatServiceImpl implements ChatService {
	private final ChatRepository chatFactory;

	@Autowired
	public ChatServiceImpl(ChatRepository chatFactory) {
		this.chatFactory = chatFactory;
	}

	@Override
	public List<User> getUsers() {
		return chatFactory.readUsers();
	}

	@Override
	public User getUser(String username) {
		return getUsers().stream().filter(user -> Objects.equals(user.getName(), username)).toList().get(0);
	}

	@Override
	public Channel getChannel(String channelName) {
		return getChannels().stream().filter(channel -> Objects.equals(channel.getName(), channelName)).toList().get(0);
	}


	@Override
	public User addUser(String name, LocalDate birthdate, Role role) {
		return chatFactory.createUser(new User(name, birthdate, role));
	}

	//	@Override
	//	public User addUser(String name, LocalDate birthdate) {
	//		return chatFactory.createUser(new User(name, birthdate));
	//	}

	@Override
	public List<Channel> getChannels() {
		return chatFactory.readChannels();
	}

	@Override
	public Channel addChannel(String name, String description) {
		return chatFactory.createChannel(new Channel(name, description));
	}
}
