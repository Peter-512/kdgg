package be.kdg.programming3.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Post {
	private User user;
	transient private Channel channel;
	private String content;
	private int upVotes;
	private LocalDate date;

	public Post(User user, Channel channel, String content, int upVotes, LocalDate date) {
		this(user, channel, content);
		this.upVotes = upVotes;
		this.date = date;
	}

	public Post(User user, Channel channel, String content) {
		this.user = user;
		this.channel = channel;
		this.content = content;
		upVotes = 0;
		date = LocalDate.now();
	}

	public void upVote() {
		upVotes++;
	}

	public void downVote() {
		upVotes--;
	}

	@Override
	public String toString() {
		return String.format("""
				%s: %s
				%d - %s
				""", user.getName(), content, upVotes, date);
	}
}
