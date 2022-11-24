package be.kdg.programming3.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Channel {
	private List<User> users;
	private List<Post> posts;
	private String name;
	private String description;
	private int id;
	private double upVotesRatio;

	public Channel(String name, String description) {
		this.users = new ArrayList<>();
		this.posts = new ArrayList<>();
		this.name = name;
		this.upVotesRatio = 0;
		this.description = description;
	}

	public Channel(int id, String name, String description) {
		this(name, description);
		this.id = id;
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
