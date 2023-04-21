package be.kdg.programming5.controllers.api;

import be.kdg.programming5.config.security.AdminOnly;
import be.kdg.programming5.controllers.api.dtos.ChannelDTO;
import be.kdg.programming5.controllers.api.dtos.NewUserDTO;
import be.kdg.programming5.controllers.api.dtos.UpdatedUserDTO;
import be.kdg.programming5.controllers.api.dtos.UserDTO;
import be.kdg.programming5.exceptions.ChannelNotFoundException;
import be.kdg.programming5.exceptions.UserNotFoundException;
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
			final User user = userService.getUser(id).orElseThrow(() -> new UserNotFoundException(id));
			return ResponseEntity.ok(modelMapper.map(user, UserDTO.class));
		} catch (UserNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping ("/{id}/channels")
	public ResponseEntity<List<ChannelDTO>> getChannelsOfUser(@PathVariable Long id) {
		final List<ChannelDTO> channels;
		try {
			final User user = userService.getUser(id).orElseThrow(() -> new UserNotFoundException(id));
			channels = user.getChannels()
			               .stream()
			               .map(channel -> modelMapper.map(channel, ChannelDTO.class))
			               .toList();
		} catch (UserNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
		if (channels.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(channels);
	}

	@AdminOnly
	@DeleteMapping ("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
		try {
			userService.getUser(id).orElseThrow(() -> new UserNotFoundException(id));
			userService.deleteUser(id);
			return ResponseEntity.noContent().build();
		} catch (UserNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping ("/{id}")
	public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UpdatedUserDTO userDTO) {
		try {
			final User user = userService.getUser(id).orElseThrow(() -> new UserNotFoundException(id));
			userService.updateUser(id, userDTO.getName(), userDTO.getBirthdate(), userDTO.getRole());
			return ResponseEntity.ok(modelMapper.map(user, UserDTO.class));
		} catch (ChannelNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping
	public ResponseEntity<UserDTO> createUser(@Valid @RequestBody NewUserDTO newUserDTO) {
		final User user = userService.addUser(newUserDTO.getName(), newUserDTO.getBirthdate(), newUserDTO.getRole(), newUserDTO.getPassword());
		return ResponseEntity.ok(modelMapper.map(user, UserDTO.class));
	}
}
