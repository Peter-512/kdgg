package be.kdg.programming3.service.channels;

import be.kdg.programming3.domain.Channel;
import be.kdg.programming3.repository.channels.ChannelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Profile ({"list", "dev", "em"})
public class ChannelServiceImpl implements ChannelService {
	private final Logger logger;
	private final ChannelRepository channelRepository;

	@Autowired
	public ChannelServiceImpl(ChannelRepository channelRepository) {
		this.logger = LoggerFactory.getLogger(ChannelServiceImpl.class);
		this.channelRepository = channelRepository;
	}

	@Override
	public List<Channel> getChannels() {
		return channelRepository.findAll();
	}

	@Override
	public Optional<Channel> getChannel(Long id) {
		return channelRepository.findById(id);
	}

	@Override
	public Channel addChannel(String name, String description) {
		return channelRepository.createChannel(new Channel(name, description));
	}

	@Override
	public void deleteChannel(Long id) {
		getChannel(id).ifPresent(channelRepository::deleteChannel);
	}

	@Override
	public void setPostUpvoteCount(int upVotes, Long postID) {
		channelRepository.findAll().forEach(channel -> {
			channel.getPosts().stream()
			       .filter(post -> post.getPostID().equals(postID))
			       .findFirst()
			       .ifPresent(post -> post.setUpVotes(upVotes));
		});
	}

	@Override
	public long getPostsCountOfChannel(long channelID) {
		return channelRepository.countByChannelID(channelID);
	}
}
