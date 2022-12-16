package be.kdg.programming3.controllers;


import be.kdg.programming3.domain.session.PageVisit;
import be.kdg.programming3.service.channels.ChannelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping
public class HomeController {
	private final Logger logger;
	private final ChannelService channelService;
	private final ErrorController errorController;
	private final SessionHistoryController sessionHistoryController;

	@Autowired
	public HomeController(ChannelService channelService, SessionHistoryController sessionHistoryController) {
		this.channelService = channelService;
		logger = LoggerFactory.getLogger(this.getClass());
		errorController = new ErrorController();
		this.sessionHistoryController = sessionHistoryController;
	}

	@GetMapping
	public ModelAndView showHomeView(HttpServletRequest request) {
		logger.info("Controller is running showHomeView!");
		final ModelAndView modelAndView = new ModelAndView("index");
		modelAndView.addObject("channels", channelService.getChannels());
		modelAndView.addObject("dateFormatter", DateTimeFormatter.ofPattern("d. M. yyyy"));
		sessionHistoryController.add(new PageVisit(request.getRequestURL().toString()));
		return modelAndView;
	}

	@GetMapping ("/*/error")
	public ModelAndView showErrorView() {
		return errorController.showErrorView(HttpStatus.NOT_FOUND);
	}
}
