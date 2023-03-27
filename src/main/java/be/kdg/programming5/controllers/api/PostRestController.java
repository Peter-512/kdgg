package be.kdg.programming5.controllers.api;

import be.kdg.programming5.config.security.ModOrAdminOnly;
import be.kdg.programming5.controllers.api.dtos.NewPostDTO;
import be.kdg.programming5.controllers.api.dtos.PostDTO;
import be.kdg.programming5.controllers.api.dtos.UpdatedPostDTO;
import be.kdg.programming5.exceptions.ChannelNotFoundException;
import be.kdg.programming5.exceptions.PostNotFoundException;
import be.kdg.programming5.model.Channel;
import be.kdg.programming5.model.Post;
import be.kdg.programming5.model.User;
import be.kdg.programming5.service.channels.ChannelService;
import be.kdg.programming5.service.posts.PostService;
import be.kdg.programming5.service.users.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping ("/api/posts")
public class PostRestController {
	private final PostService postService;
	private final ChannelService channelService;
	private final UserService userService;
	private final ModelMapper modelMapper;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@PostMapping
	public ResponseEntity<PostDTO> createPost(@RequestBody @Valid NewPostDTO postDTO) {
		try {
			final String username = SecurityContextHolder.getContext().getAuthentication().getName();
			User user = userService.getUser(username).orElseThrow(() -> new UsernameNotFoundException(username));
			Channel channel = channelService.getChannel(postDTO.getChannelID())
			                                .orElseThrow(() -> new ChannelNotFoundException(postDTO.getChannelID()));
			final Post createdPost = postService.addPost(user, channel, postDTO.getContent());
			logger.info("Created post with id: " + createdPost.getPostID());
			return ResponseEntity.ok(modelMapper.map(createdPost, PostDTO.class));
		} catch (UsernameNotFoundException | ChannelNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@PatchMapping ("/{id}")
	public ResponseEntity<PostDTO> updatePostUpVotes(@RequestBody UpdatedPostDTO updatedPostDTO, @PathVariable Long id) {
		try {
			final Post post = postService.getPost(id).orElseThrow(() -> new PostNotFoundException(id));
			postService.setPostUpvoteCount(id, updatedPostDTO.getUpVotes());
			post.setUpVotes(updatedPostDTO.getUpVotes());
			return ResponseEntity.ok(modelMapper.map(post, PostDTO.class));
		} catch (PostNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@ModOrAdminOnly
	@DeleteMapping ("/{id}")
	public ResponseEntity<Void> deletePost(@PathVariable Long id) {
		try {
			postService.getPost(id).orElseThrow(() -> new PostNotFoundException(id));
			postService.deletePost(id);
			return ResponseEntity.noContent().build();
		} catch (PostNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}
}
