package be.kdg.programming3.service.channels;

import be.kdg.programming3.domain.Channel;
import be.kdg.programming3.repository.channels.ChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Profile ({"list", "dev"})
public class ChannelServiceImpl implements ChannelService {
	private final ChannelRepository channelRepository;

	@Autowired
	public ChannelServiceImpl(ChannelRepository channelRepository) {
		this.channelRepository = channelRepository;
	}

	@Override
	public List<Channel> getChannels() {
		return channelRepository.findAll();
	}

	@Override
	public Optional<Channel> getChannel(Long id) {
		final List<Channel> channels = getChannels().stream()
		                                            .filter(channel -> Objects.equals(channel.getChannelID(), id))
		                                            .toList();
		return channels.isEmpty() ? Optional.empty() : Optional.of(channels.get(0));
	}

	@Override
	public Channel addChannel(String name, String description) {
		return channelRepository.createChannel(new Channel(name, description));
	}

	@Override
	public void deleteChannel(Long id) {
		//		TODO
	}
}
