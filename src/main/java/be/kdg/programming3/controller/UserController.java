package be.kdg.programming3.controller;

import be.kdg.programming3.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping ("/users")
public class UserController {
	private final ChatService chatService;

	@Autowired
	public UserController(ChatService chatService) {
		this.chatService = chatService;
	}

	@GetMapping
	public ModelAndView show() {
		final ModelAndView modelAndView = new ModelAndView("users");
		modelAndView.addObject("users", chatService.getUsers());
		return modelAndView;
	}
}
