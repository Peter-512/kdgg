package be.kdg.programming5.controllers.mvc;


import be.kdg.programming5.controllers.mvc.viewmodels.UserLoginViewModel;
import be.kdg.programming5.controllers.mvc.viewmodels.UserViewModel;
import be.kdg.programming5.model.Role;
import be.kdg.programming5.model.session.PageVisit;
import be.kdg.programming5.service.channels.ChannelService;
import be.kdg.programming5.service.users.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class HomeController {
	private final Logger logger;
	private final ChannelService channelService;
	private final SessionHistoryController sessionHistoryController;
	private final UserService userService;

	@Autowired
	public HomeController(ChannelService channelService, SessionHistoryController sessionHistoryController, UserService userService) {
		this.channelService = channelService;
		this.userService = userService;
		logger = LoggerFactory.getLogger(this.getClass());
		this.sessionHistoryController = sessionHistoryController;
	}

	@GetMapping ("/login")
	public ModelAndView login(ModelAndView modelAndView, @RequestParam (required = false) String error) {
		logger.info("Controller is running login!");
		modelAndView.addObject("user", new UserLoginViewModel());
		modelAndView.addObject("error", error);
		modelAndView.setViewName("login");
		return modelAndView;
	}

	@PostMapping ("/signup")
	public ModelAndView processSignup(@Valid @ModelAttribute ("user") UserViewModel user, BindingResult errors, HttpServletRequest request, CsrfToken csrfToken) throws ServletException {
		logger.info("Controller is running sign up!");
		if (errors.hasErrors()) {
			errors.getAllErrors().forEach(error -> logger.error(error.toString()));
			final ModelAndView modelAndView = new ModelAndView("users/sign-up");
			modelAndView.addObject("roles", Role.values());
			return modelAndView;
		}
		userService.addUser(user.getName(), user.getBirthdate(), user.getRole(), user.getPassword());
		request.login(user.getName(), user.getPassword());
		return new ModelAndView("redirect:users");
	}

	@GetMapping ("/signup")
	public ModelAndView showAddUserView(HttpServletRequest request) {
		logger.info("Controller is running showAddUserView!");
		final ModelAndView modelAndView = new ModelAndView("users/sign-up");
		modelAndView.addObject("user", new UserViewModel());
		modelAndView.addObject("roles", Role.values());
		sessionHistoryController.add(new PageVisit(request.getRequestURL().toString()));
		return modelAndView;
	}

	@GetMapping
	public ModelAndView showHomeView(HttpServletRequest request) {
		logger.info("Controller is running showHomeView!");
		final ModelAndView modelAndView = new ModelAndView("index");
		modelAndView.addObject("channels", channelService.getChannels());
		modelAndView.addObject("dateFormatter", DateTimeFormatter.ofPattern("d. MMMM yyyy"));
		modelAndView.addObject("timeFormatter", DateTimeFormatter.ofPattern("HH:mm"));
		modelAndView.addObject("now", LocalDateTime.now());
		sessionHistoryController.add(new PageVisit(request.getRequestURL().toString()));
		return modelAndView;
	}
}
