package be.kdg.programming3.service.channels;

import be.kdg.programming3.domain.Channel;
import be.kdg.programming3.repository.channels.ChannelRepositoryHibernate;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Profile ("prod")
public class ChannelServiceHibernate implements ChannelService {
	private final ChannelRepositoryHibernate channelRepository;

	@Autowired
	public ChannelServiceHibernate(ChannelRepositoryHibernate channelRepository) {
		this.channelRepository = channelRepository;
	}

	@Override
	public List<Channel> getChannels() {
		return Lists.newArrayList(channelRepository.findAll());
	}

	@Override
	public Optional<Channel> getChannel(Long id) {
		return channelRepository.findById(id);
	}

	@Override
	public Channel addChannel(String name, String description) {
		return channelRepository.save(new Channel(name, description));
	}

	@Override
	public void deleteChannel(Long id) {
		channelRepository.deleteById(id);
	}
}
