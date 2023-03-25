package be.kdg.programming5.controllers.mvc;

import be.kdg.programming5.controllers.mvc.viewmodels.ChannelViewModel;
import be.kdg.programming5.controllers.mvc.viewmodels.PostViewModel;
import be.kdg.programming5.exceptions.ChannelNotFoundException;
import be.kdg.programming5.model.Channel;
import be.kdg.programming5.model.session.PageVisit;
import be.kdg.programming5.service.channels.ChannelService;
import be.kdg.programming5.util.JsonWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping ("/channels")
public class ChannelController {
	private final Logger logger;
	private final ChannelService channelService;
	private final SessionHistoryController sessionHistoryController;
	private final JsonWriter jsonWriter;

	@Autowired
	public ChannelController(ChannelService channelService, SessionHistoryController sessionHistoryController, JsonWriter jsonWriter) {
		this.channelService = channelService;
		this.jsonWriter = jsonWriter;
		logger = LoggerFactory.getLogger(this.getClass());
		this.sessionHistoryController = sessionHistoryController;
	}

	@GetMapping
	public ModelAndView showChannelsView(HttpServletRequest request) {
		logger.info("Controller is running showChannelsView!");
		final ModelAndView modelAndView = new ModelAndView("channels/channels");
		modelAndView.addObject("channels", channelService.getChannels());
		modelAndView.addObject("channelService", channelService);
		sessionHistoryController.add(new PageVisit(request.getRequestURL().toString()));
		return modelAndView;
	}

	@GetMapping ("/{id}")
	public ModelAndView showChannelView(@PathVariable Long id, HttpServletRequest request, @AuthenticationPrincipal User user) {
		final Channel channel = channelService.getChannel(id).orElseThrow(() -> new ChannelNotFoundException(id));

		logger.info("Controller is running showChannelView with channel {}!", channel.getName());

		final ModelAndView modelAndView = new ModelAndView("channels/channel");
		modelAndView.addObject("channel", channel);
		modelAndView.addObject("dateFormatter", DateTimeFormatter.ofPattern("d. MMMM yyyy"));
		modelAndView.addObject("timeFormatter", DateTimeFormatter.ofPattern("HH:mm"));
		modelAndView.addObject("now", LocalDateTime.now());
		modelAndView.addObject("viewModel", new PostViewModel());
		modelAndView.addObject("hasJoined", channel.getUsers()
		                                           .stream()
		                                           .map(be.kdg.programming5.model.User::getName)
		                                           .anyMatch(u -> u.equals(user.getUsername())));
		sessionHistoryController.add(new PageVisit(request.getRequestURL().toString()));
		return modelAndView;
	}

	@GetMapping ("/add")
	public ModelAndView showAddChannelView(HttpServletRequest request) {
		logger.info("Controller is running showAddChannelView!");
		final ModelAndView modelAndView = new ModelAndView("channels/add-channel");
		modelAndView.addObject("channel", new ChannelViewModel());
		sessionHistoryController.add(new PageVisit(request.getRequestURL().toString()));
		return modelAndView;
	}

	@PostMapping
	public ModelAndView processAddChannel(@Valid @ModelAttribute ("channel") ChannelViewModel channel, BindingResult errors, CsrfToken csrfToken) {
		logger.info("Controller is running processAddChannel!");
		if (errors.hasErrors()) {
			errors.getAllErrors().forEach(error -> logger.error(error.toString()));
			return new ModelAndView("channels/add-channel");
		}

		channelService.addChannel(channel.getName(), channel.getDescription());
		return new ModelAndView("redirect:channels");
	}

	@GetMapping (value = "/download", produces = "application/json")
	public ResponseEntity<InputStreamResource> downloadJSONFile() {
		final byte[] buf = jsonWriter.getJsonBytes(channelService.getChannels());
		return ResponseEntity
				.ok()
				.contentLength(buf.length)
				.contentType(MediaType.parseMediaType("application/octet-stream"))
				.header("Content-Disposition", "attachment; filename=\"channels.json\"")
				.body(new InputStreamResource(new ByteArrayInputStream(buf)));
	}
}
