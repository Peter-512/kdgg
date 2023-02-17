package be.kdg.programming5.service.channels;

import be.kdg.programming5.domain.Channel;
import be.kdg.programming5.domain.User;

import java.util.List;
import java.util.Optional;

public interface ChannelService {
	List<Channel> getChannels();

	Optional<Channel> getChannel(Long id);

	Channel addChannel(String name, String description);

	void deleteChannel(Long id);

	void setPostUpvoteCount(int upVotes, Long postID);

	long getPostsCountOfChannel(long channelID);

	void addPost(Long channelID, String content, User user);
}
