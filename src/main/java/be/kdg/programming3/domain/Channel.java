package be.kdg.programming3.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table (name = "channels")
public class Channel {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column (name = "channel_id", nullable = false)
	private Long channelID;
	@ManyToMany (mappedBy = "channels")
	private List<User> users;
	@OneToMany (mappedBy = "channel")
	private List<Post> posts;
	@Column (name = "channel_name", nullable = false)
	private String name;
	@Column (nullable = false)
	private String description;
	transient private double upVotesRatio;

	public Channel(String name, String description) {
		users = new ArrayList<>();
		posts = new ArrayList<>();
		this.name = name;
		this.upVotesRatio = 0;
		this.description = description;
	}

	public Channel(Long id, String name, String description) {
		this(name, description);
		this.channelID = id;
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
