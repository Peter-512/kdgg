package be.kdg.programming3;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class User {
	transient private List<Channel> channels;
	transient private List<Post> posts;
	private String name;
	private LocalDate birthDate;
	private Role role;

	public User(String name, LocalDate birthDate, Role role) {
		this.channels = new ArrayList<>();
		this.posts = new ArrayList<>();
		this.name = name;
		this.birthDate = birthDate;
		this.role = role;
	}

	public static User create(String name, LocalDate birthDate, Role role) {
		return new User(name, birthDate, role);
	}

	public static User createRandom() {
		Faker faker = new Faker();
		return new User(faker.name().fullName(),
				LocalDate.ofInstant(faker.date().birthday().toInstant(),
						ZoneId.systemDefault()), Role.randomRole());
	}

	public String getName() {
		return name;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public Role getRole() {
		return role;
	}

	public List<Channel> getChannels() {
		return channels;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void createPost(Channel channel, String content) {
		Post post = new Post(this, channel, content);
		posts.add(post);
		channel.createPost(post);
	}

	public void createPost(Channel channel, Post post) {
		posts.add(post);
		channel.createPost(post);
	}

	public void joinChannel(Channel channel) {
		channels.add(channel);
		channel.addUser(this);
	}

	@Override
	public String toString() {
		return String.format("%s (%s) - %s\n", name, birthDate, role);
	}
}
