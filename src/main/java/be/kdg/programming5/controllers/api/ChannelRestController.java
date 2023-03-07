package be.kdg.programming5.controllers.api;

import be.kdg.programming5.controllers.api.dtos.*;
import be.kdg.programming5.exceptions.ChannelNotFoundException;
import be.kdg.programming5.model.Channel;
import be.kdg.programming5.model.Post;
import be.kdg.programming5.model.User;
import be.kdg.programming5.service.channels.ChannelService;
import be.kdg.programming5.service.posts.PostService;
import be.kdg.programming5.service.users.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
	private final PostService postService;

	public ChannelRestController(ChannelService channelService, ModelMapper modelMapper, UserService userService, PostService postService) {
		this.channelService = channelService;
		this.modelMapper = modelMapper;
		this.userService = userService;
		this.postService = postService;
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
			final Channel channel = channelService.getChannel(id).orElseThrow();
			return ResponseEntity.ok(modelMapper.map(channel, ChannelDTO.class));
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping ("/{id}/users")
	public ResponseEntity<List<UserDTO>> getUsersOfChannel(@PathVariable Long id) {
		final List<UserDTO> users;
		try {
			final Channel channel = channelService.getChannel(id).orElseThrow();
			users = channel.getUsers()
			               .stream()
			               .map(user -> modelMapper.map(user, UserDTO.class))
			               .toList();
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
		if (users.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(users);
	}

	@DeleteMapping ("/{id}")
	public ResponseEntity<Void> deleteChannel(@PathVariable Long id) {
		try {
			channelService.getChannel(id).orElseThrow();
			channelService.deleteChannel(id);
			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}

	@PatchMapping ("/{id}")
	public ResponseEntity<ChannelDTO> updateChannel(@PathVariable Long id, @Valid @RequestBody UpdatedChannelDTO channelDTO) {
		try {
			channelService.getChannel(id).orElseThrow();
			final Channel newChannel = channelService.updateChannel(id, channelDTO.getDescription());
			return ResponseEntity.ok(modelMapper.map(newChannel, ChannelDTO.class));
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping
	public ResponseEntity<ChannelDTO> createChannel(@Valid @RequestBody NewChannelDTO newChannelDTO) {
		final Channel newChannel = channelService.addChannel(newChannelDTO.getName(), newChannelDTO.getDescription());
		return ResponseEntity.ok(modelMapper.map(newChannel, ChannelDTO.class));
	}

	@PostMapping ("/{channelID}/posts")
	public ResponseEntity<PostDTO> createPost(@PathVariable Long channelID, @RequestBody @Valid NewPostDTO newPostDTO) {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		final String username = authentication.getName();
		final User user = userService.getUser(username)
		                             .orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
		final Channel channel = channelService.getChannel(channelID)
		                                      .orElseThrow(() -> new ChannelNotFoundException(channelID));

		final Post post = postService.addPost(user, channel, newPostDTO.getContent());
		return ResponseEntity.ok(new PostDTO(post.getPostID(), post.getContent(), user.getName(), user.getUserID(), post.getUpVotes(), post.getPostedAt()));
	}
}
