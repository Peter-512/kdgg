package be.kdg.programming5.controllers.api;

import be.kdg.programming5.model.Channel;
import be.kdg.programming5.model.Role;
import be.kdg.programming5.model.User;
import be.kdg.programming5.repository.ChannelRepository;
import be.kdg.programming5.repository.UserRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance (TestInstance.Lifecycle.PER_CLASS)
class ChannelRestControllerTest {

	private Channel c1;
	private Channel c2;
	private Channel c3;
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ChannelRestController channelRestController;
	@Autowired
	private ChannelRepository channelRepository;
	@Autowired
	private UserRepository userRepository;

	@BeforeAll
	void setup() {
		final User u1 = userRepository.save(new User("user1", LocalDate.of(2000, 1, 1), Role.ADMIN, "password"));
		final User u2 = userRepository.save(new User("user2", LocalDate.of(2000, 1, 1), Role.ADMIN, "password"));
		final User u3 = userRepository.save(new User("user3", LocalDate.of(2000, 1, 1), Role.ADMIN, "password"));
		final User u4 = userRepository.save(new User("user4", LocalDate.of(2000, 1, 1), Role.ADMIN, "password"));
		c1 = channelRepository.save(new Channel("channel1", "description1"));
		c2 = channelRepository.save(new Channel("channel2", "description2"));
		c3 = channelRepository.save(new Channel("channel3", "description3"));
		u1.joinChannel(c1);
		u2.joinChannel(c1);

		u1.joinChannel(c2);
		u3.joinChannel(c2);
		u4.joinChannel(c2);

		userRepository.save(u1);
		userRepository.save(u2);
		userRepository.save(u3);
		userRepository.save(u4);
	}

	@AfterAll
	void tearDown() {
		channelRepository.deleteAll();
		userRepository.deleteAll();
	}

	@Test
	void getUsersOfChannel() throws Exception {
		mockMvc.perform(get("/api/channels/{id}/users", c1.getChannelID()))
		       .andExpect(status().isOk())
		       .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
		       .andExpect(jsonPath("$", hasSize(2)))
		       .andExpect(jsonPath("$[*].name", containsInAnyOrder("user1", "user2")));

		mockMvc.perform(get("/api/channels/{id}/users", c2.getChannelID()))
		       .andExpect(status().isOk())
		       .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
		       .andExpect(jsonPath("$", hasSize(3)))
		       .andExpect(jsonPath("$[*].name", containsInAnyOrder("user1", "user3", "user4")));
	}

	@Test
	void getUsersOfChannelNotFound() throws Exception {
		mockMvc.perform(get("/api/channels/999/users"))
		       .andExpect(status().isNotFound());
	}

	@Test
	void getUsersOfChannelNoContent() throws Exception {
		mockMvc.perform(get("/api/channels/" + c3.getChannelID() + "/users"))
		       .andExpect(status().isNoContent());
	}
}
