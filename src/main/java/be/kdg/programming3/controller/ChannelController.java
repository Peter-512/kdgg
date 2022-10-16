package be.kdg.programming3.controller;

import be.kdg.programming3.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping ("/channels")
public class ChannelController {
	private final ChatService chatService;

	@Autowired
	public ChannelController(ChatService chatService) {
		this.chatService = chatService;
	}

	@GetMapping
	public ModelAndView show() {
		final ModelAndView modelAndView = new ModelAndView("channels");
		modelAndView.addObject("channels", chatService.getChannels());
		return modelAndView;
	}
}
