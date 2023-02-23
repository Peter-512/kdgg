package be.kdg.programming5.controllers.mvc;

import be.kdg.programming5.model.session.PageVisit;
import be.kdg.programming5.model.session.SessionHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.format.DateTimeFormatter;

@Controller
public class SessionHistoryController {
	private final SessionHistory sessionHistory;

	@Autowired
	public SessionHistoryController(SessionHistory sessionHistory) {
		this.sessionHistory = sessionHistory;
	}

	public boolean add(PageVisit pageVisit) {
		return sessionHistory.getPageVisits().add(pageVisit);
	}

	@GetMapping ("/session-history")
	public ModelAndView showSessionHistoryView() {
		return new ModelAndView("session-history")
				.addObject("sessions", sessionHistory.getPageVisits())
				.addObject("dateTimeFormatter", DateTimeFormatter.ofPattern("d. M. yyyy, HH:mm:ss"));
	}
}
