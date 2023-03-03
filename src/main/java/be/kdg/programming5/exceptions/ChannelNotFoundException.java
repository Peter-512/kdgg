package be.kdg.programming5.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus (value = HttpStatus.NOT_FOUND, reason = "Channel not found")
public class ChannelNotFoundException extends RuntimeException {
	public ChannelNotFoundException(long id) {
		super(String.format("Channel %s not found.", id));
	}
}
