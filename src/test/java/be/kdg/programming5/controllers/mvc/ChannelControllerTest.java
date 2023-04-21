package be.kdg.programming5.controllers.mvc;

import be.kdg.programming5.controllers.mvc.viewmodels.ChannelViewModel;
import be.kdg.programming5.model.Channel;
import be.kdg.programming5.repository.ChannelRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.instanceOf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance (TestInstance.Lifecycle.PER_CLASS)
class ChannelControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ChannelRepository channelRepository;

	@BeforeEach
	void setUp() {
		channelRepository.save(new Channel("Channel 1", "Description 1"));
		channelRepository.save(new Channel("Channel 2", "Description 2"));
		channelRepository.save(new Channel("Channel 3", "Description 3"));
	}

	@AfterEach
	void tearDown() {
		channelRepository.deleteAll();
	}

	@Test
	@WithMockUser
	void showAddChannelView() throws Exception {
		mockMvc.perform(get("/channels/add"))
		       .andExpect(status().isOk())
		       .andExpect(view().name("channels/add-channel"))
		       .andExpect(model().attributeExists("channel"))
		       .andExpect(model().attribute("channel", instanceOf(ChannelViewModel.class)));
	}
}
