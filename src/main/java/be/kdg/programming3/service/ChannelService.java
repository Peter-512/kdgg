package be.kdg.programming3.service;

import be.kdg.programming3.domain.Channel;

import java.util.List;
import java.util.Optional;

public interface ChannelService {
	List<Channel> getChannels();

	Optional<Channel> getChannel(String channelName);

	Channel addChannel(String name, String description);
}
