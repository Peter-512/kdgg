package be.kdg.programming3.presentation;

import be.kdg.programming3.domain.Role;
import be.kdg.programming3.service.UserService;
import com.github.javafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping ("/users")
public class UserController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final UserService userService;

	@Autowired
	public UserController(UserService chatService) {
		this.userService = chatService;
	}

	@GetMapping
	public ModelAndView showUsersView() {
		logger.info("Controller is running showUsersView!");
		final ModelAndView modelAndView = new ModelAndView("users/users");
		modelAndView.addObject("users", userService.getUsers());
		modelAndView.addObject("dateFormatter", DateTimeFormatter.ofPattern("d. LLLL yyyy"));
		return modelAndView;
	}

	@GetMapping ("/{username}")
	public ModelAndView showUserView(@PathVariable String username) {
		logger.info(String.format("Controller is running showUserView with user %s!", username));
		final ModelAndView modelAndView = new ModelAndView("users/user");
		modelAndView.addObject("user", userService.getUser(username));
		modelAndView.addObject("dateFormatter", DateTimeFormatter.ofPattern("d. LLLL yyyy"));
		return modelAndView;
	}

	@GetMapping ("/add")
	public ModelAndView showAddUserView() {
		logger.info("Controller is running showAddUserView!");
		final ModelAndView modelAndView = new ModelAndView("users/add-user");
		modelAndView.addObject("faker", new Faker());
		modelAndView.addObject("roles", Role.values());
		return modelAndView;
	}

	@PostMapping
	public ModelAndView processAddChannel(
			@RequestParam ("name") String name,
			@RequestParam ("birthdate") @DateTimeFormat (iso = DateTimeFormat.ISO.DATE) LocalDate birthdate,
			@RequestParam ("role") Role role) {
		logger.info("Controller is running processAddChannel!");
		userService.addUser(name, birthdate, role);
		return new ModelAndView("redirect:users");
	}
}
