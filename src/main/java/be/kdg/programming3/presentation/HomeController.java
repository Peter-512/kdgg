package be.kdg.programming3.presentation;


import be.kdg.programming3.service.ChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping
public class HomeController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final ChatService chatService;

	@Autowired
	public HomeController(ChatService chatService) {
		this.chatService = chatService;
	}

	@GetMapping
	public ModelAndView showHomeView() {
		logger.info("Controller is running showHomeView!");
		final ModelAndView modelAndView = new ModelAndView("index");
		modelAndView.addObject("chatService", chatService);
		modelAndView.addObject("dateFormatter", DateTimeFormatter.ofPattern("d. LLLL yyyy"));
		return modelAndView;
	}
}
