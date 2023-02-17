package be.kdg.programming5.domain;

import com.google.gson.annotations.Expose;
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
	@ManyToMany
	@JoinTable (name = "user_channels", joinColumns = @JoinColumn (name = "user_id"), inverseJoinColumns = @JoinColumn (name = "channel_id"))
	private List<User> users;
	@Expose
	@OneToMany (cascade = CascadeType.ALL, mappedBy = "channel")
	private List<Post> posts;
	@Expose
	@Column (name = "channel_name", nullable = false)
	private String name;
	@Column (nullable = false)
	@Expose
	private String description;
	@Expose
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
				id: %d
				\t- %s
				""", name, channelID, description);
	}

	public double calculateUpvoteRatio() {
		final int upVotes = posts.stream().mapToInt(Post::getUpVotes).sum();
		return posts.isEmpty() ? 0 : (double) upVotes / posts.size();
	}
}
