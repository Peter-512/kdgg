package be.kdg.programming5.controllers.mvc;

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

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance (TestInstance.Lifecycle.PER_CLASS)
class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private UserRepository userRepository;

	@BeforeEach
	void setup() {
		userRepository.save(new User("user1", LocalDate.of(2000, 1, 1), Role.ADMIN, "password"));
		userRepository.save(new User("user2", LocalDate.of(2000, 1, 1), Role.ADMIN, "password"));
		userRepository.save(new User("user3", LocalDate.of(2000, 1, 1), Role.ADMIN, "password"));
		userRepository.save(new User("user4", LocalDate.of(2000, 1, 1), Role.ADMIN, "password"));
	}

	@AfterEach
	void tearDown() {
		userRepository.deleteAll();
	}

	@Test
	void showUsersView() throws Exception {
		mockMvc.perform(get("/users"))
		       .andExpect(status().isOk())
		       .andExpect(view().name("users/users"))
		       .andExpect(model().attributeExists("users"))
		       .andExpect(model().attribute("users", hasSize(4)))
		       .andExpect(model().attributeExists("dateFormatter"));
	}
}
