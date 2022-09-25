package be.kdg.programming3;

import com.github.javafaker.Faker;

import java.time.LocalDate;

public class Post {
	private User user;
	private Channel channel;
	private String content;
	private int upVotes;
	private LocalDate date;

	public Post(User user, Channel channel, String content) {
		this.user = user;
		this.channel = channel;
		this.content = content;
		this.upVotes = 0;
		this.date = LocalDate.now(); // ? could be faker data
	}

	public static Post createRandom(User user, Channel channel) {
		Faker faker = new Faker();
		return new Post(user, channel, faker.lorem().sentence());
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

	public LocalDate getDate() {
		return date;
	}

	@Override
	public String toString() {
		return String.format("Post(%s): %s\n", date, content);
	}
}
