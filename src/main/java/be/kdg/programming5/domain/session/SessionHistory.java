package be.kdg.programming5.domain.session;

import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;

@Component
@SessionScope
@Getter
public class SessionHistory {
	private final List<PageVisit> pageVisits;

	public SessionHistory() {
		this.pageVisits = new ArrayList<>();
	}
}
