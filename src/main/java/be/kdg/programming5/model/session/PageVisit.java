package be.kdg.programming5.model.session;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PageVisit {
	private final LocalDateTime timestamp;
	private final String url;

	public PageVisit(String url) {
		this.timestamp = LocalDateTime.now();
		this.url = url;
	}
}
