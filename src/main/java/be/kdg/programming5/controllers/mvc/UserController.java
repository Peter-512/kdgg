package be.kdg.programming5.controllers.mvc;

import be.kdg.programming5.exceptions.UserNotFoundException;
import be.kdg.programming5.model.User;
import be.kdg.programming5.model.session.PageVisit;
import be.kdg.programming5.service.users.UserService;
import be.kdg.programming5.util.JsonWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.time.format.DateTimeFormatter;

@Controller
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


	@GetMapping
	public ModelAndView showUsersView(HttpServletRequest request) {
		logger.info("Controller is running showUsersView!");
		final ModelAndView modelAndView = new ModelAndView("users/users");
		modelAndView.addObject("users", userService.getUsersWithPosts());
		modelAndView.addObject("dateFormatter", dateTimeFormatter);
		sessionHistoryController.add(new PageVisit(request.getRequestURL().toString()));
		return modelAndView;
	}

	@GetMapping ("/{id}")
	public ModelAndView showUserView(@PathVariable Long id, HttpServletRequest request) {
		final User user = userService.getUser(id)
		                             .orElseThrow(() -> new UserNotFoundException(id));

		logger.info("Controller is running showUserView with user {}!", user.getName());

		final ModelAndView modelAndView = new ModelAndView("users/user");
		modelAndView.addObject("user", user);
		modelAndView.addObject("dateFormatter", dateTimeFormatter);
		sessionHistoryController.add(new PageVisit(request.getRequestURL().toString()));
		return modelAndView;
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
