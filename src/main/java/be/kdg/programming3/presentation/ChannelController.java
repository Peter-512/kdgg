package be.kdg.programming3.presentation;

import be.kdg.programming3.domain.Channel;
import be.kdg.programming3.presentation.viewmodel.ChannelViewModel;
import be.kdg.programming3.service.ChannelService;
import com.github.javafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

@RestController
@RequestMapping ("/channels")
public class ChannelController {
	private final Logger logger;
	private final ChannelService channelService;
	private final ErrorController errorController;

	@Autowired
	public ChannelController(ChannelService chatService) {
		this.channelService = chatService;
		logger = LoggerFactory.getLogger(this.getClass());
		errorController = new ErrorController();
	}

	@GetMapping
	public ModelAndView showChannelsView() {
		logger.info("Controller is running showChannelsView!");
		final ModelAndView modelAndView = new ModelAndView("channels/channels");
		modelAndView.addObject("channels", channelService.getChannels());
		return modelAndView;
	}

	@GetMapping ("/{channelName}")
	public ModelAndView showChannelView(@PathVariable String channelName) {
		logger.info(String.format("Controller is running showChannelView with channel %s!", channelName));
		final Optional<Channel> channel = channelService.getChannel(channelName);
		if (channel.isEmpty()) {
			logger.error(String.format("No channel with name %s found.", channelName));
			return errorController.showErrorView(HttpStatus.NOT_FOUND);
		}

		final ModelAndView modelAndView = new ModelAndView("channels/channel");
		modelAndView.addObject("channel", channel.get());
		modelAndView.addObject("dateFormatter", DateTimeFormatter.ofPattern("d. MMMM yyyy"));
		return modelAndView;
	}

	@GetMapping ("/add")
	public ModelAndView showAddChannelView() {
		logger.info("Controller is running showAddChannelView!");
		final ModelAndView modelAndView = new ModelAndView("channels/add-channel");
		modelAndView.addObject("channel", new ChannelViewModel());
		modelAndView.addObject("faker", new Faker());
		return modelAndView;
	}

	@PostMapping
	public ModelAndView processAddChannel(Channel channel) {
		logger.info("Controller is running processAddChannel!");
		channelService.addChannel(channel.getName(), channel.getDescription());
		return new ModelAndView("redirect:channels");
	}
}
