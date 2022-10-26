package be.kdg.programming3.service;

import be.kdg.programming3.domain.Role;
import be.kdg.programming3.domain.User;

import java.time.LocalDate;
import java.util.List;

public interface UserService {
	List<User> getUsers();

	User getUser(String username);

	User addUser(String name, LocalDate birthdate, Role role);
}
