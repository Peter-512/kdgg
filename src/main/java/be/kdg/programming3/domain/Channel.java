package be.kdg.programming3.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Channel {
	private final List<User> users;
	private final List<Post> posts;
	private final String name;
	private final String description;
	private double upVotesRatio;

	public Channel(String name, String description) {
		this.users = new ArrayList<>();
		this.posts = new ArrayList<>();
		this.name = name;
		this.upVotesRatio = 0;
		this.description = description;
	}

	public void createPost(Post post) {
		posts.add(post);
	}

	public void addUser(User user) {
		users.add(user);
	}

	@Override
	public String toString() {
		return String.format("""
				Channel %s
				\t- %s
				""", name, description);
	}

	public void calculateUpvoteRatio() {
		final int upVotes = posts.stream().mapToInt(Post::getUpVotes).sum();
		setUpVotesRatio((double) upVotes / posts.size());
	}
}
