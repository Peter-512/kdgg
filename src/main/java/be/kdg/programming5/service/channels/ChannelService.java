package be.kdg.programming5.service.channels;

import be.kdg.programming5.model.Channel;
import be.kdg.programming5.model.User;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public interface ChannelService {
	List<Channel> getChannels();

	Optional<Channel> getChannel(Long id);

	Channel addChannel(String name, String description);

	void deleteChannel(Long id);

	void setPostUpvoteCount(int upVotes, Long postID);

	long getPostsCountOfChannel(long channelID);

	Channel updateChannel(Long id, String description);

	void joinChannel(User user, Channel channel);

	void leaveChannel(User user, Channel channel);

	void handleChannelCsv(InputStream inputStream);
}
