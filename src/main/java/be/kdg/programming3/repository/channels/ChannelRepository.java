package be.kdg.programming3.repository.channels;

import be.kdg.programming3.domain.Channel;

import java.util.List;

public interface ChannelRepository {
	List<Channel> findAll();

	Channel createChannel(Channel channel);

	void updateChannel(Channel channel);

	boolean deleteChannel(Channel channel);
}
