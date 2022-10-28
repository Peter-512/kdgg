package be.kdg.programming3.presentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorController {
	private final Logger logger;

	public ErrorController() {
		logger = LoggerFactory.getLogger(this.getClass());
	}

	@GetMapping ("/error")
	@ResponseStatus (value = HttpStatus.NOT_FOUND)
	public ModelAndView showErrorView(HttpStatus status) {
		logger.info(String.format("Controller is running showErrorView with status %s", status));
		return new ModelAndView("error").addObject("status", status.getReasonPhrase());
	}
}
