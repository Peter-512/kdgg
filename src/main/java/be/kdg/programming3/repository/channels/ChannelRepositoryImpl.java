package be.kdg.programming3.repository.channels;

import be.kdg.programming3.domain.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Profile ("old")
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
	public void updateChannel(Channel channel) {
		//		TODO
	}

	@Override
	public boolean deleteChannel(Channel channel) {
		return channels.remove(channel);
	}

	@Override
	public List<Channel> findAll() {
		return channels;
	}
}
