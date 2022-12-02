package be.kdg.programming3.service.channels;

import be.kdg.programming3.domain.Channel;

import java.util.List;
import java.util.Optional;

public interface ChannelService {
	List<Channel> getChannels();

	Optional<Channel> getChannel(Long id);

	Channel addChannel(String name, String description);

	void deleteChannel(Long id);
}
