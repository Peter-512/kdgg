package be.kdg.programming3.controller;

import be.kdg.programming3.domain.Role;
import be.kdg.programming3.service.ChatService;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;

@RestController
@RequestMapping ("/add-user")
public class AddUserController {
	private final ChatService chatService;

	@Autowired
	public AddUserController(ChatService chatService) {
		this.chatService = chatService;
	}

	@GetMapping
	public ModelAndView show() {
		final ModelAndView modelAndView = new ModelAndView("add-user");
		modelAndView.addObject("faker", new Faker());
		modelAndView.addObject("roles", Role.values());
		return modelAndView;
	}

	@PostMapping
	public ModelAndView processAddChannel(
			@RequestParam ("name") String name,
			@RequestParam ("birthdate") @DateTimeFormat (iso = DateTimeFormat.ISO.DATE) LocalDate birthdate,
			@RequestParam ("role") Role role) {
		chatService.addUser(name, birthdate, role);
		return new ModelAndView("users").addObject("users", chatService.getUsers());
	}

}
