package be.kdg.programming3.controller;

import be.kdg.programming3.domain.Channel;
import be.kdg.programming3.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping ("/add-channel")
public class AddChannelController {
	private final ChatService chatService;

	@Autowired
	public AddChannelController(ChatService chatService) {
		this.chatService = chatService;
	}

	@GetMapping
	public ModelAndView show() {
		return new ModelAndView("add-channel");
	}

	@PostMapping
	public ModelAndView processAddChannel(Channel channel) {
		chatService.addChannel(channel.getName(), channel.getDescription());
		return new ModelAndView("channels").addObject("channels", chatService.getChannels());
	}
}
