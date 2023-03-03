package be.kdg.programming5.controllers.mvc;

import be.kdg.programming5.exceptions.ChannelNotFoundException;
import be.kdg.programming5.exceptions.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionController {
	private final Logger logger;

	@Autowired
	public GlobalExceptionController() {
		this.logger = LoggerFactory.getLogger(this.getClass());
	}

	@ResponseStatus (HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler ({DataAccessException.class, IllegalStateException.class})
	public String handleException(DataAccessException e, IllegalStateException e2) {
		logger.error(e.getMessage());
		logger.error(e2.getMessage());
		return "error";
	}

	@ResponseStatus (HttpStatus.NOT_FOUND)
	@ExceptionHandler ({ChannelNotFoundException.class, UserNotFoundException.class})
	public String handleException(Exception e) {
		logger.error(e.getMessage());
		return "error/404";
	}
}
