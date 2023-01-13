package be.kdg.programming3.repository.channels;

import be.kdg.programming3.domain.Channel;
import be.kdg.programming3.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Profile ("list")
public class ChannelRepositoryList implements ChannelRepository {
	private static Long idCounter = 0L;
	private final List<Channel> channels;
	private final Logger logger;

	@Autowired
	public ChannelRepositoryList() {
		this.logger = LoggerFactory.getLogger(ChannelRepositoryList.class);
		channels = new ArrayList<>();
	}

	@Override
	public Channel createChannel(Channel channel) {
		channel.setChannelID(idCounter++);
		channels.add(channel);
		logger.info("Channel created: " + channel);
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
	public Optional<Channel> findById(Long id) {
		return channels.stream().filter(channel -> channel.getChannelID().equals(id)).findFirst();
	}

	@Override
	public long countByChannelID(Long channelID) {
		return findById(channelID).orElseThrow().getPosts().size();
	}

	@Override
	public void createPost(Long channelID, String content, User user) {
		user.createPost(findById(channelID).orElseThrow(), content);
	}

	@Override
	public List<Channel> findAll() {
		return channels;
	}
}
