package be.kdg.programming5.controllers.api;

import be.kdg.programming5.model.Role;
import be.kdg.programming5.model.User;
import be.kdg.programming5.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc (addFilters = false)
@TestInstance (TestInstance.Lifecycle.PER_CLASS)
class UserRestControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private UserRepository userRepository;
	private User u1;

	@BeforeEach
	void setup() {
		u1 = userRepository.save(new User("user1", LocalDate.of(2000, 1, 1), Role.ADMIN, "password"));
		userRepository.save(new User("user2", LocalDate.of(2000, 1, 1), Role.ADMIN, "password"));
		userRepository.save(new User("user3", LocalDate.of(2000, 1, 1), Role.ADMIN, "password"));
		userRepository.save(new User("user4", LocalDate.of(2000, 1, 1), Role.ADMIN, "password"));
	}

	@AfterEach
	void tearDown() {
		userRepository.deleteAll();
	}

	@Test
	void deleteUserNotFound() throws Exception {
		mockMvc.perform(delete("/api/users/{id}", 9999))
		       .andExpect(status().isNotFound());

		assertEquals(4, userRepository.count());
	}

	@Test
	void deleteUserNoContent() throws Exception {
		mockMvc.perform(delete("/api/users/{id}", u1.getUserID()))
		       .andExpect(status().isNoContent());

		assertEquals(3, userRepository.count());
	}
}
