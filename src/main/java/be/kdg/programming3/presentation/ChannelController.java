package be.kdg.programming3.presentation;

import be.kdg.programming3.domain.Channel;
import be.kdg.programming3.service.ChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping ("/channels")
public class ChannelController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final ChatService chatService;

	@Autowired
	public ChannelController(ChatService chatService) {
		this.chatService = chatService;
	}

	@GetMapping
	public ModelAndView showChannelsView() {
		logger.info("Controller is running showChannelsView!");
		final ModelAndView modelAndView = new ModelAndView("channels");
		modelAndView.addObject("channels", chatService.getChannels());
		return modelAndView;
	}

	@GetMapping ("/{channelName}")
	public ModelAndView showChannelView(@PathVariable String channelName) {
		logger.info(String.format("Controller is running showChannelView with channel %s!", channelName));
		final ModelAndView modelAndView = new ModelAndView("channel");
		modelAndView.addObject("channel", chatService.getChannel(channelName));
		modelAndView.addObject("dateFormatter", DateTimeFormatter.ofPattern("d. LLLL yyyy"));
		return modelAndView;
	}

	@GetMapping ("/add")
	public ModelAndView showAddChannelView() {
		logger.info("Controller is running showAddChannelView!");
		return new ModelAndView("add-channel");
	}

	@PostMapping
	public ModelAndView processAddChannel(Channel channel) {
		logger.info("Controller is running processAddChannel!");
		chatService.addChannel(channel.getName(), channel.getDescription());
		return new ModelAndView("redirect:channels");
	}
}
