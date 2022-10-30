package be.kdg.programming3.presentation;

import be.kdg.programming3.domain.Channel;
import be.kdg.programming3.domain.session.PageVisit;
import be.kdg.programming3.presentation.viewmodel.ChannelViewModel;
import be.kdg.programming3.service.ChannelService;
import com.github.javafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@RestController
@RequestMapping ("/channels")
public class ChannelController {
	private final Logger logger;
	private final ChannelService channelService;
	private final ErrorController errorController;
	private final SessionHistoryController sessionHistoryController;

	@Autowired
	public ChannelController(ChannelService channelService, SessionHistoryController sessionHistoryController) {
		this.channelService = channelService;
		logger = LoggerFactory.getLogger(this.getClass());
		errorController = new ErrorController();
		this.sessionHistoryController = sessionHistoryController;
	}

	@GetMapping
	public ModelAndView showChannelsView(HttpServletRequest request) {
		logger.info("Controller is running showChannelsView!");
		final ModelAndView modelAndView = new ModelAndView("channels/channels");
		modelAndView.addObject("channels", channelService.getChannels());
		sessionHistoryController.add(new PageVisit(request.getRequestURL().toString()));
		return modelAndView;
	}

	@GetMapping ("/{channelName}")
	public ModelAndView showChannelView(@PathVariable String channelName, HttpServletRequest request) {
		logger.info(String.format("Controller is running showChannelView with channel %s!", channelName));
		final Optional<Channel> channel = channelService.getChannel(channelName);
		if (channel.isEmpty()) {
			logger.error(String.format("No channel with name %s found.", channelName));
			return errorController.showErrorView(HttpStatus.NOT_FOUND);
		}

		final ModelAndView modelAndView = new ModelAndView("channels/channel");
		modelAndView.addObject("channel", channel.get());
		modelAndView.addObject("dateFormatter", DateTimeFormatter.ofPattern("d. MMMM yyyy"));
		sessionHistoryController.add(new PageVisit(request.getRequestURL().toString()));
		return modelAndView;
	}

	@GetMapping ("/add")
	public ModelAndView showAddChannelView(HttpServletRequest request) {
		logger.info("Controller is running showAddChannelView!");
		final ModelAndView modelAndView = new ModelAndView("channels/add-channel");
		modelAndView.addObject("channel", new ChannelViewModel());
		modelAndView.addObject("faker", new Faker());
		sessionHistoryController.add(new PageVisit(request.getRequestURL().toString()));
		return modelAndView;
	}

	@PostMapping
	public ModelAndView processAddChannel(@Valid @ModelAttribute ("channel") ChannelViewModel channel, BindingResult errors) { // @Valid @ModelAttribute ("channel") in front of channelViewModel
		logger.info("Controller is running processAddChannel!");
		if (errors.hasErrors()) {
			errors.getAllErrors().forEach(error -> logger.error(error.toString()));
			return new ModelAndView("channels/add-channel").addObject("faker", new Faker());
		}

		channelService.addChannel(channel.getName(), channel.getDescription());
		return new ModelAndView("redirect:channels");
	}
}
