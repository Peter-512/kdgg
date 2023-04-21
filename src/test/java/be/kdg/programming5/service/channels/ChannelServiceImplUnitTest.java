package be.kdg.programming5.service.channels;

import be.kdg.programming5.exceptions.ChannelNotFoundException;
import be.kdg.programming5.model.Channel;
import be.kdg.programming5.repository.ChannelRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@SpringBootTest
class ChannelServiceImplUnitTest {
	@Autowired
	private ChannelService channelService;

	@MockBean
	private ChannelRepository channelRepository;

	@Test
	void updateChannelDescriptionShouldUpdateDescriptionOfChannel() {
		var id = 1L;
		var desc = "new desc";
		var mockedChannel = mock(Channel.class);

		given(channelRepository.findById(id)).willReturn(Optional.of(mockedChannel));

		channelService.updateChannel(id, desc);

		verify(mockedChannel).setDescription(desc);
		verify(channelRepository).save(mockedChannel);
	}

	@Test
	void updateChannelDescriptionOfNonExistingChannelShouldThrow() {
		var id = 1L;
		var desc = "new desc";

		given(channelRepository.findById(id)).willReturn(Optional.empty());

		assertThrows(ChannelNotFoundException.class, () -> channelService.updateChannel(id, desc));
	}
}
