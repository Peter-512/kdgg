package be.kdg.programming3.service;

import be.kdg.programming3.domain.Channel;
import be.kdg.programming3.repository.ChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ChannelServiceImpl implements ChannelService {
	private final ChannelRepository channelRepository;

	@Autowired
	public ChannelServiceImpl(ChannelRepository channelRepository) {
		this.channelRepository = channelRepository;
	}

	@Override
	public List<Channel> getChannels() {
		return channelRepository.readChannels();
	}

	@Override
	public Optional<Channel> getChannel(String channelName) {
		final List<Channel> channels = getChannels().stream()
		                                            .filter(channel -> Objects.equals(channel.getName(), channelName))
		                                            .toList();
		return channels.isEmpty() ? Optional.empty() : Optional.of(channels.get(0));
	}

	@Override
	public Channel addChannel(String name, String description) {
		return channelRepository.createChannel(new Channel(name, description));
	}
}
