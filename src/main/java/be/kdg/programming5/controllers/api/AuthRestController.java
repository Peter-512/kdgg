package be.kdg.programming5.controllers.api;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/api")
public class AuthRestController {
@GetMapping("/authenticated")
	public boolean isAuthenticated() {
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	return !Objects.equals(authentication.getName(), "anonymousUser");
	}
}
