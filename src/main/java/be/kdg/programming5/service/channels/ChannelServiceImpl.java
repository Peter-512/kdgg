package be.kdg.programming5.service.channels;

import be.kdg.programming5.exceptions.ChannelNotFoundException;
import be.kdg.programming5.model.Channel;
import be.kdg.programming5.model.User;
import be.kdg.programming5.repository.ChannelRepository;
import be.kdg.programming5.repository.PostRepository;
import be.kdg.programming5.repository.UserRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ChannelServiceImpl implements ChannelService {
	private final ChannelRepository channelRepository;
	private final PostRepository postRepository;
	private final UserRepository userRepository;

	@Autowired
	public ChannelServiceImpl(ChannelRepository channelRepository, PostRepository postRepository, UserRepository userRepository) {
		this.channelRepository = channelRepository;
		this.postRepository = postRepository;
		this.userRepository = userRepository;
	}

	@Override
	@Transactional
	public List<Channel> getChannels() {
		return Lists.newArrayList(channelRepository.findAll());
	}

	@Override
	@Transactional
	public Optional<Channel> getChannel(Long id) {
		return channelRepository.findById(id);
	}

	@Override
	@Transactional
	public Channel addChannel(String name, String description) {
		return channelRepository.save(new Channel(name, description));
	}

	@Override
	@Transactional
	public void deleteChannel(Long id) {
		channelRepository.deleteById(id);
	}

	@Override
	@Transactional
	public void setPostUpvoteCount(int upVotes, Long postID) {
		postRepository.updateUpVotesByPostID(upVotes, postID);
	}

	@Override
	@Transactional
	public long getPostsCountOfChannel(long id) {
		return postRepository.countByChannel_ChannelID(id);
	}

	@Override
	@Transactional
	public Channel updateChannel(Long id, String description) {
		Channel channel = getChannel(id).orElseThrow(() -> new ChannelNotFoundException(id));
		channel.setDescription(description);
		return channelRepository.save(channel);
	}

	@Override
	@Transactional
	public void joinChannel(User user, Channel channel) {
		user.joinChannel(channel);
		userRepository.save(user);
	}

	@Override
	@Transactional
	public void leaveChannel(User user, Channel channel) {
		user.leaveChannel(channel);
		userRepository.save(user);
	}
}
