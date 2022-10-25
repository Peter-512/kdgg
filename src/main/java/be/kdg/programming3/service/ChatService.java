package be.kdg.programming3.service;

import be.kdg.programming3.domain.Channel;
import be.kdg.programming3.domain.Role;
import be.kdg.programming3.domain.User;

import java.time.LocalDate;
import java.util.List;

public interface ChatService {
	List<User> getUsers();

	User getUser(String username);

	Channel getChannel(String channelName);

	User addUser(String name, LocalDate birthdate, Role role);

	//	User addUser(String name, LocalDate birthdate);

	List<Channel> getChannels();

	Channel addChannel(String name, String description);
}
