package be.kdg.programming3.presentation;


import be.kdg.programming3.service.ChannelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping
public class HomeController {
	private final Logger logger;
	private final ChannelService channelService;
	private final ErrorController errorController;

	@Autowired
	public HomeController(ChannelService chatService) {
		this.channelService = chatService;
		logger = LoggerFactory.getLogger(this.getClass());
		errorController = new ErrorController();
	}

	@GetMapping
	public ModelAndView showHomeView() {
		logger.info("Controller is running showHomeView!");
		final ModelAndView modelAndView = new ModelAndView("index");
		modelAndView.addObject("channels", channelService.getChannels());
		modelAndView.addObject("dateFormatter", DateTimeFormatter.ofPattern("d. M. yyyy"));
		return modelAndView;
	}

	@GetMapping ("*")
	public ModelAndView showErrorView() {
		return errorController.showErrorView(HttpStatus.NOT_FOUND);
	}
}
