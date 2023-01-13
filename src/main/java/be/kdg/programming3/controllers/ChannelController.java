package be.kdg.programming3.controllers;

import be.kdg.programming3.domain.Channel;
import be.kdg.programming3.domain.session.PageVisit;
import be.kdg.programming3.exceptions.ChannelNotFoundException;
import be.kdg.programming3.presentation.viewmodel.ChannelViewModel;
import be.kdg.programming3.service.channels.ChannelService;
import be.kdg.programming3.util.JsonWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@RestController
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

	@ExceptionHandler (ChannelNotFoundException.class)
	public ModelAndView handleChannelNotFoundException(Exception e, ModelAndView modelAndView) {
		logger.error(e.getMessage());
		modelAndView.addObject("em", e.getMessage());
		return modelAndView;
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
	public ModelAndView showChannelView(@PathVariable Long id, HttpServletRequest request) {
		final Optional<Channel> channel = channelService.getChannel(id);
		if (channel.isEmpty()) {
			final String errorMessage = String.format("No channel with id %s found.", id);
			logger.error(errorMessage);
			throw new ChannelNotFoundException(errorMessage);
		}
		logger.info(String.format("Controller is running showChannelView with channel %s!",
				channelService.getChannel(id).get().getName()));

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
		sessionHistoryController.add(new PageVisit(request.getRequestURL().toString()));
		return modelAndView;
	}

	@PostMapping
	public ModelAndView processAddChannel(@Valid @ModelAttribute ("channel") ChannelViewModel channel, BindingResult errors) {
		logger.info("Controller is running processAddChannel!");
		if (errors.hasErrors()) {
			errors.getAllErrors().forEach(error -> logger.error(error.toString()));
			return new ModelAndView("channels/add-channel");
		}

		channelService.addChannel(channel.getName(), channel.getDescription());
		return new ModelAndView("redirect:channels");
	}

	@DeleteMapping ("delete/{id}")
	public ModelAndView deleteChannel(@PathVariable Long id) {
		logger.info(String.format("Channel %s getting deleted", channelService.getChannel(id)));
		channelService.deleteChannel(id);
		return new ModelAndView("redirect:/channels");
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

	@PatchMapping ("/upvote/{id}/{upVotes}")
	public ModelAndView updateVotesOnPost(@PathVariable Long id, @PathVariable int upVotes, HttpServletRequest request) {
		logger.info(String.format("Post #%d getting upvoted", id));
		channelService.setPostUpvoteCount(upVotes, id);
		return new ModelAndView("redirect:" + request.getHeader("Referer"));
	}
}
