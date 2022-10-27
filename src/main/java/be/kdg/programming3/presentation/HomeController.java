package be.kdg.programming3.presentation;


import be.kdg.programming3.service.ChannelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping
public class HomeController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final ChannelService channelService;

	@Autowired
	public HomeController(ChannelService chatService) {
		this.channelService = chatService;
	}

	@GetMapping
	public ModelAndView showHomeView() {
		logger.info("Controller is running showHomeView!");
		final ModelAndView modelAndView = new ModelAndView("index");
		modelAndView.addObject("channels", channelService.getChannels());
		modelAndView.addObject("dateFormatter", DateTimeFormatter.ofPattern("d. LLLL yyyy"));
		return modelAndView;
	}

	@GetMapping ("/error")
	@ResponseStatus (value = HttpStatus.NOT_FOUND)
	public ModelAndView showErrorView(HttpHeaders status) {
		logger.info(String.format("Controller is running showErrorView with status %s", status));
		return new ModelAndView("error").addObject("status", status);
	}
}
