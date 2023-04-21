package be.kdg.programming5.controllers.api;

import be.kdg.programming5.config.security.AdminOnly;
import be.kdg.programming5.controllers.api.dtos.*;
import be.kdg.programming5.exceptions.ChannelNotFoundException;
import be.kdg.programming5.model.Channel;
import be.kdg.programming5.model.User;
import be.kdg.programming5.service.channels.ChannelService;
import be.kdg.programming5.service.users.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping ("/api/channels")
public class ChannelRestController {
	private final ChannelService channelService;
	private final ModelMapper modelMapper;
	private final UserService userService;
	private final Logger logger;

	public ChannelRestController(ChannelService channelService, ModelMapper modelMapper, UserService userService) {
		this.channelService = channelService;
		this.modelMapper = modelMapper;
		this.userService = userService;
		logger = LoggerFactory.getLogger(this.getClass());
	}

	@GetMapping
	public ResponseEntity<List<ChannelDTO>> getChannels() {
		final List<ChannelDTO> channels = channelService.getChannels()
		                                                .stream()
		                                                .map(channel -> modelMapper.map(channel, ChannelDTO.class))
		                                                .toList();
		if (channels.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(channels);
	}

	@GetMapping ("/{id}")
	public ResponseEntity<ChannelDTO> getChannel(@PathVariable Long id) {
		try {
			final Channel channel = channelService.getChannel(id).orElseThrow(() -> new ChannelNotFoundException(id));
			return ResponseEntity.ok(modelMapper.map(channel, ChannelDTO.class));
		} catch (ChannelNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping ("/{id}/users")
	public ResponseEntity<List<UserDTO>> getUsersOfChannel(@PathVariable Long id) {
		final List<UserDTO> users;
		try {
			final Channel channel = channelService.getChannel(id).orElseThrow(() -> new ChannelNotFoundException(id));
			users = channel.getUsers()
			               .stream()
			               .map(user -> modelMapper.map(user, UserDTO.class))
			               .toList();
		} catch (ChannelNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
		if (users.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(users);
	}

	@AdminOnly
	@DeleteMapping ("/{id}")
	public ResponseEntity<Void> deleteChannel(@PathVariable Long id) {
		try {
			channelService.getChannel(id).orElseThrow(() -> new ChannelNotFoundException(id));
			channelService.deleteChannel(id);
			return ResponseEntity.noContent().build();
		} catch (ChannelNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@PatchMapping ("/{id}")
	public ResponseEntity<ChannelDTO> updateChannel(@PathVariable Long id, @Valid @RequestBody UpdatedChannelDTO channelDTO) {
		try {
			channelService.getChannel(id).orElseThrow();
			final Channel newChannel = channelService.updateChannel(id, channelDTO.getDescription());
			return ResponseEntity.ok(modelMapper.map(newChannel, ChannelDTO.class));
		} catch (ChannelNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping (produces = {"application/json", "application/xml"})
	public ResponseEntity<ChannelDTO> createChannel(@Valid @RequestBody NewChannelDTO newChannelDTO) {
		final Channel newChannel = channelService.addChannel(newChannelDTO.getName(), newChannelDTO.getDescription());
		return ResponseEntity.ok(modelMapper.map(newChannel, ChannelDTO.class));
	}

	private String getAuthenticatedUsername() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}

	@PatchMapping ("/{channelID}/join")
	public ResponseEntity<Void> joinChannel(@PathVariable Long channelID, @RequestBody JoinOrLeaveChannelDTO joinOrLeaveChannelDTO) {
		final String username = getAuthenticatedUsername();
		final User user = userService.getUser(username)
		                             .orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
		final Channel channel = channelService.getChannel(channelID)
		                                      .orElseThrow(() -> new ChannelNotFoundException(channelID));

		logger.info("Joining channel {} with user {}", channel.getName(), user.getName());
		channelService.joinChannel(user, channel);
		return ResponseEntity.ok().build();
	}

	@PatchMapping ("/{channelID}/leave")
	public ResponseEntity<Void> leaveChannel(@PathVariable Long channelID, @RequestBody JoinOrLeaveChannelDTO joinOrLeaveChannelDTO) {
		final String username = getAuthenticatedUsername();
		final User user = userService.getUser(username)
		                             .orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
		final Channel channel = channelService.getChannel(channelID)
		                                      .orElseThrow(() -> new ChannelNotFoundException(channelID));

		logger.info("Leaving channel {} with user {}", channel.getName(), user.getName());
		channelService.leaveChannel(user, channel);
		return ResponseEntity.ok().build();
	}
}
