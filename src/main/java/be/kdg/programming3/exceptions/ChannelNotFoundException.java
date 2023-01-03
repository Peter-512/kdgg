package be.kdg.programming3.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus (value = HttpStatus.NOT_FOUND, reason = "Channel not found")
public class ChannelNotFoundException extends RuntimeException {
	public ChannelNotFoundException(String message) {
		super(message);
	}
}
