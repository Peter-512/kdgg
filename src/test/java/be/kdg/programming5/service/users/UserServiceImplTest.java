package be.kdg.programming5.service.users;

import be.kdg.programming5.exceptions.UserNotFoundException;
import be.kdg.programming5.model.Role;
import be.kdg.programming5.model.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
@TestInstance (TestInstance.Lifecycle.PER_CLASS)
class UserServiceImplTest {

	@Autowired
	private UserService userService;
	private User user;

	@BeforeEach
	void setUp() {
		user = userService.addUser("user", LocalDate.of(1992, 11, 19), Role.ADMIN, "password");
	}

	@AfterEach
	void tearDown() {
		userService.getUsers().stream().map(User::getUserID).forEach(userService::deleteUser);
	}

	@Test
	void updateExistingUserShouldProperlyUpdatePassedFields() {
		final User updatedUser = Assertions.assertDoesNotThrow(() -> userService.updateUser(user.getUserID(), "newUsername", LocalDate.of(2000, 1, 1), Role.MOD));

		Assertions.assertEquals("newUsername", updatedUser.getName());
		Assertions.assertEquals(LocalDate.of(2000, 1, 1), updatedUser.getBirthdate());
		Assertions.assertEquals(Role.MOD, updatedUser.getRole());
	}

	@Test
	void updateNonExistingUserShouldThrowException() {
		Assertions.assertThrows(UserNotFoundException.class, () -> userService.updateUser(999999L, "newUsername", LocalDate.of(2000, 1, 1), Role.MOD));
	}
}
