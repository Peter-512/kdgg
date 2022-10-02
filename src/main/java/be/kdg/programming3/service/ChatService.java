package be.kdg.programming3.service;

import be.kdg.programming3.domain.Channel;
import be.kdg.programming3.domain.Role;
import be.kdg.programming3.domain.User;

import java.time.LocalDate;
import java.util.List;

public interface ChatService {
	List<User> getUsers();

	User addUser(String name, LocalDate birthdate, Role role);

	List<Channel> getChannels();

	Channel addChannel(String name, String description);
}
