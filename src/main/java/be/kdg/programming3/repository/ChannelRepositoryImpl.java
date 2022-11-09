package be.kdg.programming3.repository;

import be.kdg.programming3.domain.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ChannelRepositoryImpl implements ChannelRepository {
	private final List<Channel> channels;

	@Autowired
	public ChannelRepositoryImpl() {
		channels = new ArrayList<>();
	}

	@Override
	public Channel createChannel(Channel channel) {
		channels.add(channel);
		return channel;
	}

	@Override
	public List<Channel> readChannels() {
		return channels;
	}

	@Override
	public boolean deleteChannel(Channel channel) {
		return channels.removeIf(c -> c == channel);
	}
}
