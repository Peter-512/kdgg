package be.kdg.programming5.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus (value = HttpStatus.NOT_FOUND, reason = "Post not found")
public class PostNotFoundException extends RuntimeException {
	public PostNotFoundException(long id) {
		super(String.format("Post %s not found.", id));
	}
}
