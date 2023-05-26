package be.kdg.programming5.service.channels;

import be.kdg.programming5.controllers.api.dtos.NewChannelDTO;
import be.kdg.programming5.exceptions.ChannelNotFoundException;
import be.kdg.programming5.model.Channel;
import be.kdg.programming5.model.User;
import be.kdg.programming5.repository.ChannelRepository;
import be.kdg.programming5.repository.PostRepository;
import com.google.common.collect.Lists;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ChannelServiceImpl implements ChannelService {
	private final ChannelRepository channelRepository;
	private final PostRepository postRepository;
	private final ModelMapper modelMapper;
	private final Logger logger = LoggerFactory.getLogger(ChannelServiceImpl.class);

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
	}

	@Override
	@Transactional
	public void leaveChannel(User user, Channel channel) {
		user.leaveChannel(channel);
	}

	@Override
	@Async
	public void handleChannelCsv(InputStream inputStream) {
		try (InputStreamReader reader = new InputStreamReader(inputStream)) {
			List<NewChannelDTO> channels = new CsvToBeanBuilder<NewChannelDTO>(reader)
					.withType(NewChannelDTO.class)
					.build()
					.parse();
			Thread.sleep(3000);

			final Channel[] map = modelMapper.map(channels, Channel[].class);
			channelRepository.saveAll(Arrays.stream(map).toList());
		} catch (IOException | InterruptedException e) {
			logger.error(e.getMessage());
		}
	}
}
