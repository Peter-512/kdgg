package be.kdg.programming3.repository;

import be.kdg.programming3.domain.Channel;

import java.util.List;

public interface ChannelRepository {
	Channel createChannel(Channel channel);

	List<Channel> readChannels();

	boolean deleteChannel(Channel channel);
}
