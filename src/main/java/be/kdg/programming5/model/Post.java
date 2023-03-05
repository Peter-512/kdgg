package be.kdg.programming5.model;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table (name = "posts")
public class Post {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column (name = "post_id", nullable = false)
	private Long postID;
	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn (name = "user_id", nullable = false)
	private User user;
	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn (name = "channel_id", nullable = false)
	private Channel channel;
	@Expose
	@Column (nullable = false)
	private String content;
	@Expose
	@Column (nullable = false)
	private int upVotes;
	@Expose
	@Column (nullable = false)
	private LocalDateTime postedAt;

	public Post(Long postID, User user, Channel channel, String content, int upVotes, LocalDateTime postedAt) {
		this(user, channel, content);
		this.upVotes = upVotes;
		this.postedAt = postedAt;
		this.postID = postID;
	}

	public Post(User user, Channel channel, String content) {
		this.user = user;
		this.channel = channel;
		this.content = content;
		upVotes = 0;
		postedAt = LocalDateTime.now();
		user.createPost(channel, this);
	}

	@Override
	public String toString() {
		return String.format("""
				userID: %d
				%s: %s
				%d - %s
				""", user.getUserID(), user.getName(), content, upVotes, postedAt);
	}
}
