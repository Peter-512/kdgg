package be.kdg.programming3.presentation;

import be.kdg.programming3.domain.Role;
import be.kdg.programming3.domain.User;
import be.kdg.programming3.domain.session.PageVisit;
import be.kdg.programming3.presentation.viewmodel.UserViewModel;
import be.kdg.programming3.service.users.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@RestController
@RequestMapping ("/users")
public class UserController {
	private final Logger logger;
	private final UserService userService;
	private final DateTimeFormatter dateTimeFormatter;
	private final ErrorController errorController;
	private final SessionHistoryController sessionHistoryController;

	@Autowired
	public UserController(UserService userService, SessionHistoryController sessionHistoryController) {
		this.userService = userService;
		logger = LoggerFactory.getLogger(this.getClass());
		dateTimeFormatter = DateTimeFormatter.ofPattern("d. MMMM yyyy");
		errorController = new ErrorController();
		this.sessionHistoryController = sessionHistoryController;
	}

	@GetMapping
	public ModelAndView showUsersView(HttpServletRequest request) {
		logger.info("Controller is running showUsersView!");
		final ModelAndView modelAndView = new ModelAndView("users/users");
		modelAndView.addObject("users", userService.getUsers());
		modelAndView.addObject("dateFormatter", dateTimeFormatter);
		sessionHistoryController.add(new PageVisit(request.getRequestURL().toString()));
		return modelAndView;
	}

	@GetMapping ("/{id}")
	public ModelAndView showUserView(@PathVariable Long id, HttpServletRequest request) {
		final Optional<User> user = userService.getUser(id);
		if (user.isEmpty()) {
			logger.error(String.format("User %s not found.", id));
			return errorController.showErrorView(HttpStatus.NOT_FOUND);
		}
		logger.info(String.format("Controller is running showUserView with user %s!", userService.getUser(id)
		                                                                                         .get()
		                                                                                         .getName()));

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
}
