package be.kdg.programming3.service;

import be.kdg.programming3.domain.Channel;
import be.kdg.programming3.domain.Role;
import be.kdg.programming3.domain.User;
import be.kdg.programming3.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
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
	public User addUser(String name, LocalDate birthdate, Role role) {
		return chatFactory.createUser(new User(name, birthdate, role));
	}

	@Override
	public List<Channel> getChannels() {
		return chatFactory.readChannels();
	}

	@Override
	public Channel addChannel(String name, String description) {
		return chatFactory.createChannel(new Channel(name, description));
	}
}
