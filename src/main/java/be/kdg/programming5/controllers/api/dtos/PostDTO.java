package be.kdg.programming5.controllers.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PostDTO {
	private long postID;
	private String content;
	private String username;
	private long userID;
	private int upVotes;
	private LocalDateTime postedAt;
}
