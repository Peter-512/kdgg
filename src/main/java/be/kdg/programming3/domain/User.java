package be.kdg.programming3.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class User {
	transient private List<Channel> channels;
	transient private List<Post> posts;
	private String name;
	private LocalDate birthdate;
	private Role role;

	public User(String name, LocalDate birthdate, Role role) {
		//		this(name, birthdate);
		this.channels = new ArrayList<>();
		this.posts = new ArrayList<>();
		this.role = role;
		this.name = name;
		this.birthdate = birthdate;
	}

	//	public User(String name, LocalDate birthdate) {
	//		this.name = name;
	//		this.birthdate = birthdate;
	//		role = Role.User;
	//	}

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
		return String.format("""
								
				%s (%s) - %s""", name, birthdate, role);
	}
}
