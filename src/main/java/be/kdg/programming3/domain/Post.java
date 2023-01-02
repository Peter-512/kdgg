package be.kdg.programming3.domain;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

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
	private LocalDate date;

	public Post(User user, Channel channel, String content, int upVotes, LocalDate date) {
		this(user, channel, content);
		this.upVotes = upVotes;
		this.date = date;
	}

	public Post(User user, Channel channel, String content) {
		this.user = user;
		this.channel = channel;
		this.content = content;
		upVotes = 0;
		date = LocalDate.now();
	}

	public void upVote() {
		upVotes++;
	}

	public void downVote() {
		upVotes--;
	}

	@Override
	public String toString() {
		return String.format("""
				%s: %s
				%d - %s
				""", user.getName(), content, upVotes, date);
	}
}
