package be.kdg.programming3;

import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.List;

public class Channel {
	transient private List<User> users;
	transient private List<Post> posts;
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

	public static Channel createRandom() {
		Faker faker = new Faker();
		return new Channel(faker.starTrek().character(), faker.yoda().quote());
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

	public void createPost(Post post) {
		posts.add(post);
	}

	public void addUser(User user) {
		users.add(user);
	}

	@Override
	public String toString() {
		return String.format("Channel %s:\n%s\nUsers:\n%s\nPosts:\n%s", name, description, users, posts);
	}
}
