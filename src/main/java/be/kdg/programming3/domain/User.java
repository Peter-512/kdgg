package be.kdg.programming3.domain;

import be.kdg.programming3.util.PostgreSQLEnumType;
import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@TypeDef (name = "role", typeClass = PostgreSQLEnumType.class)
@Entity
@Table (name = "users")
public class User {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column (name = "user_id", nullable = false)
	private Long userID;
	@ManyToMany
	@JoinTable (name = "user_channels", joinColumns = @JoinColumn (name = "channel_id"), inverseJoinColumns = @JoinColumn (name = "user_id"))
	private List<Channel> channels;
	@OneToMany (cascade = CascadeType.ALL, mappedBy = "user")
	private List<Post> posts;
	@Expose
	@Column (name = "user_name")
	private String name;
	@Expose
	@Column
	private LocalDate birthdate;
	@Expose
	@Column
	@Enumerated (EnumType.STRING)
	@Type (type = "role")
	private Role role;

	public User(String name, LocalDate birthdate, Role role) {
		this(name, birthdate);
		this.role = role;
	}

	public User(String name, LocalDate birthdate) {
		channels = new ArrayList<>();
		posts = new ArrayList<>();
		this.name = name;
		this.birthdate = birthdate;
		role = Role.User;
	}

	public User(Long userID, String name, LocalDate birthdate, Role role) {
		this(name, birthdate, role);
		this.userID = userID;
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
		return String.format("""
										
				id: %d
					%s (%s) - %s""", userID, name, birthdate, role);
	}
}
