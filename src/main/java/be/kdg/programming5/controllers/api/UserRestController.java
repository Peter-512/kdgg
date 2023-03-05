package be.kdg.programming5.controllers.api;

import be.kdg.programming5.controllers.api.dtos.ChannelDTO;
import be.kdg.programming5.controllers.api.dtos.UpdatedUserDTO;
import be.kdg.programming5.controllers.api.dtos.UserDTO;
import be.kdg.programming5.model.User;
import be.kdg.programming5.service.users.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping ("/api/users")
public class UserRestController {
	private final UserService userService;
	private final ModelMapper modelMapper;

	public UserRestController(UserService userService, ModelMapper modelMapper) {
		this.userService = userService;
		this.modelMapper = modelMapper;
	}

	@GetMapping
	public ResponseEntity<List<UserDTO>> getUsers() {
		final List<UserDTO> users = userService.getUsers()
		                                       .stream()
		                                       .map(user -> modelMapper.map(user, UserDTO.class))
		                                       .toList();
		if (users.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(users);
	}

	@GetMapping ("/{id}")
	public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
		try {
			final User user = userService.getUser(id).orElseThrow();
			return ResponseEntity.ok(modelMapper.map(user, UserDTO.class));
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping ("/{id}/channels")
	public ResponseEntity<List<ChannelDTO>> getChannelsOfUser(@PathVariable Long id) {
		final List<ChannelDTO> channels;
		try {
			final User user = userService.getUser(id).orElseThrow();
			channels = user.getChannels()
			               .stream()
			               .map(channel -> modelMapper.map(channel, ChannelDTO.class))
			               .toList();
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
		if (channels.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(channels);
	}

	@DeleteMapping ("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
		try {
			userService.getUser(id).orElseThrow();
			userService.deleteUser(id);
			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping ("/{id}")
	public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UpdatedUserDTO userDTO) {
		try {
			final User user = userService.getUser(id).orElseThrow();
			userService.updateUser(id, userDTO.getName(), userDTO.getBirthdate(), userDTO.getRole());
			return ResponseEntity.ok(modelMapper.map(user, UserDTO.class));
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}
}