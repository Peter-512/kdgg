package be.kdg.programming5.service.channels;

import be.kdg.programming5.model.Channel;
import be.kdg.programming5.model.Post;
import be.kdg.programming5.model.User;
import be.kdg.programming5.repository.ChannelRepository;
import be.kdg.programming5.repository.PostRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChannelServiceHibernate implements ChannelService {
	private final ChannelRepository channelRepository;
	private final PostRepository postRepository;

	@Autowired
	public ChannelServiceHibernate(ChannelRepository channelRepository, PostRepository postRepository) {
		this.channelRepository = channelRepository;
		this.postRepository = postRepository;
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

	@Override
	public void setPostUpvoteCount(int upVotes, Long postID) {
		postRepository.updateUpVotesByPostID(upVotes, postID);
	}

	@Override
	public long getPostsCountOfChannel(long id) {
		return postRepository.countByChannel_ChannelID(id);
	}

	@Override
	public void addPost(Long channelID, String content, User user) {
		postRepository.save(new Post(user, getChannel(channelID).orElseThrow(), content));
	}

	@Override
	public Channel updateChannel(Long id, String description) {
		Channel channel = getChannel(id).orElseThrow();
		channel.setDescription(description);
		return channelRepository.save(channel);
	}
}