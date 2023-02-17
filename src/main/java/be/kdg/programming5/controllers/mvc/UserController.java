package be.kdg.programming5.controllers.mvc;

import be.kdg.programming5.controllers.mvc.viewmodels.UserViewModel;
import be.kdg.programming5.domain.Role;
import be.kdg.programming5.domain.User;
import be.kdg.programming5.domain.session.PageVisit;
import be.kdg.programming5.exceptions.UserNotFoundException;
import be.kdg.programming5.service.users.UserService;
import be.kdg.programming5.util.JsonWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@RestController
@RequestMapping ("/users")
public class UserController {
	private final Logger logger;
	private final UserService userService;
	private final DateTimeFormatter dateTimeFormatter;
	private final SessionHistoryController sessionHistoryController;
	private final JsonWriter jsonWriter;

	@Autowired
	public UserController(UserService userService, SessionHistoryController sessionHistoryController, JsonWriter jsonWriter) {
		this.userService = userService;
		this.jsonWriter = jsonWriter;
		logger = LoggerFactory.getLogger(this.getClass());
		dateTimeFormatter = DateTimeFormatter.ofPattern("d. MMMM yyyy");
		this.sessionHistoryController = sessionHistoryController;
	}

	@ExceptionHandler (UserNotFoundException.class)
	public ModelAndView handleUserNotFoundException(Exception e, ModelAndView modelAndView) {
		logger.error(e.getMessage());
		modelAndView.addObject("em", e.getMessage());
		return modelAndView;
	}

	@GetMapping
	public ModelAndView showUsersView(HttpServletRequest request) {
		logger.info("Controller is running showUsersView!");
		final ModelAndView modelAndView = new ModelAndView("users/users");
		modelAndView.addObject("users", userService.getUsers());
		modelAndView.addObject("dateFormatter", dateTimeFormatter);
		modelAndView.addObject("userService", userService);
		sessionHistoryController.add(new PageVisit(request.getRequestURL().toString()));
		return modelAndView;
	}

	@GetMapping ("/{id}")
	public ModelAndView showUserView(@PathVariable Long id, HttpServletRequest request) {
		final Optional<User> user = userService.getUser(id);
		if (user.isEmpty()) {
			final String errorMessage = String.format("User %s not found.", id);
			logger.error(errorMessage);
			throw new UserNotFoundException(errorMessage);
		}
		logger.info(String.format("Controller is running showUserView with user %s!",
				userService.getUser(id).get().getName()));

		final ModelAndView modelAndView = new ModelAndView("users/user");
		modelAndView.addObject("user", user.get());
		modelAndView.addObject("dateFormatter", dateTimeFormatter);
		sessionHistoryController.add(new PageVisit(request.getRequestURL().toString()));
		return modelAndView;
	}

	@GetMapping ("/add")
	public ModelAndView showAddUserView(HttpServletRequest request) {
		logger.info("Controller is running showAddUserView!");
		final ModelAndView modelAndView = new ModelAndView("users/add-user");
		modelAndView.addObject("user", new UserViewModel());
		modelAndView.addObject("roles", Role.values());
		sessionHistoryController.add(new PageVisit(request.getRequestURL().toString()));
		return modelAndView;
	}

	@PostMapping
	public ModelAndView processAddChannel(@Valid @ModelAttribute ("user") UserViewModel user, BindingResult errors) {
		logger.info("Controller is running processAddChannel!");
		if (errors.hasErrors()) {
			errors.getAllErrors().forEach(error -> logger.error(error.toString()));
			final ModelAndView modelAndView = new ModelAndView("users/add-user");
			modelAndView.addObject("roles", Role.values());
			return modelAndView;
		}
		userService.addUser(user.getName(), user.getBirthdate(), user.getRole());
		return new ModelAndView("redirect:users");
	}

	@DeleteMapping ("delete/{id}")
	public ModelAndView deleteChannel(@PathVariable Long id) {
		logger.info(String.format("User %s getting deleted", userService.getUser(id)));
		userService.deleteUser(id);
		return new ModelAndView("redirect:/users");
	}

	@GetMapping (value = "/download", produces = "application/json")
	public ResponseEntity<InputStreamResource> downloadJSONFile() {
		final byte[] buf = jsonWriter.getJsonBytes(userService.getUsers());
		return ResponseEntity
				.ok()
				.contentLength(buf.length)
				.contentType(MediaType.parseMediaType("application/octet-stream"))
				.header("Content-Disposition", "attachment; filename=\"users.json\"")
				.body(new InputStreamResource(new ByteArrayInputStream(buf)));
	}
}
