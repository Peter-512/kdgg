package be.kdg.programming5.controllers.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionController {
	private final Logger logger;

	@Autowired
	public GlobalExceptionController() {
		this.logger = LoggerFactory.getLogger(this.getClass());
	}

	@ExceptionHandler (DataAccessException.class)
	public String handleDatabaseException(Exception e) {
		logger.error(e.getMessage());
		return "error";
	}
}
