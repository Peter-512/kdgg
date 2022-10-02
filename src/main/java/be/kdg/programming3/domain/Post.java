package be.kdg.programming3.domain;

import java.time.LocalDate;

public class Post {
	private User user;
	transient private Channel channel;
	private String content;
	private int upVotes;
	private LocalDate date;

	public Post(User user, Channel channel, String content) {
		this(user, content);
		this.channel = channel;
	}

	public Post(User user, String content) {
		this.user = user;
		this.content = content;
		this.upVotes = 0;
		this.date = LocalDate.now();
	}

	public User getUser() {
		return user;
	}

	public Channel getChannel() {
		return channel;
	}

	public String getContent() {
		return content;
	}

	public int getUpVotes() {
		return upVotes;
	}

	public void setUpVotes(int votes) {
		upVotes = votes;
	}

	public LocalDate getDate() {
		return date;
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
