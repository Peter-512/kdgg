package be.kdg.programming3.domain;

import java.util.ArrayList;
import java.util.List;

public class Channel {
	private List<User> users;
	private List<Post> posts;
	private String name;
	private double upVotesRatio;
	private String description;

	public Channel(String name, String description) {
		this.users = new ArrayList<>();
		this.posts = new ArrayList<>();
		this.name = name;
		this.upVotesRatio = 0;
		this.description = description;
	}

	public List<User> getUsers() {
		return users;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public String getName() {
		return name;
	}

	public double getUpVotesRatio() {
		return upVotesRatio;
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
}
