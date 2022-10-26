package be.kdg.programming3.service;

import be.kdg.programming3.domain.Channel;

import java.util.List;

public interface ChannelService {
	List<Channel> getChannels();

	Channel getChannel(String channelName);

	Channel addChannel(String name, String description);
}
