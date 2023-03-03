package be.kdg.programming5.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus (value = HttpStatus.NOT_FOUND, reason = "User not found")
public class UserNotFoundException extends RuntimeException {
	public UserNotFoundException(long id) {
		super(String.format("User %s not found.", id));
	}
}
