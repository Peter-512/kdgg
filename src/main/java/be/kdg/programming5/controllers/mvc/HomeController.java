package be.kdg.programming5.controllers.mvc;


import be.kdg.programming5.model.session.PageVisit;
import be.kdg.programming5.service.channels.ChannelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping
public class HomeController {
	private final Logger logger;
	private final ChannelService channelService;
	private final SessionHistoryController sessionHistoryController;

	@Autowired
	public HomeController(ChannelService channelService, SessionHistoryController sessionHistoryController) {
		this.channelService = channelService;
		logger = LoggerFactory.getLogger(this.getClass());
		this.sessionHistoryController = sessionHistoryController;
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
