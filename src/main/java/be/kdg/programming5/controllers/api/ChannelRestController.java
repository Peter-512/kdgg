package be.kdg.programming5.controllers.api;

import be.kdg.programming5.controllers.api.dtos.ChannelDTO;
import be.kdg.programming5.controllers.api.dtos.UpdatedChannelDTO;
import be.kdg.programming5.controllers.api.dtos.UserDTO;
import be.kdg.programming5.model.Channel;
import be.kdg.programming5.service.channels.ChannelService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping ("/api/channels")
public class ChannelRestController {
	private final ChannelService channelService;
	private final ModelMapper modelMapper;

	public ChannelRestController(ChannelService channelService, ModelMapper modelMapper) {
		this.channelService = channelService;
		this.modelMapper = modelMapper;
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
			final Channel channel = channelService.getChannel(id).orElseThrow();
			final Channel newChannel = channelService.updateChannel(id, channelDTO.getDescription());
			return ResponseEntity.ok(modelMapper.map(newChannel, ChannelDTO.class));
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}
}
