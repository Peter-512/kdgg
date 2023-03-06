package be.kdg.programming5.controllers.api;

import be.kdg.programming5.controllers.api.dtos.PostDTO;
import be.kdg.programming5.controllers.api.dtos.UpdatedPostDTO;
import be.kdg.programming5.exceptions.PostNotFoundException;
import be.kdg.programming5.model.Post;
import be.kdg.programming5.service.posts.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/api/posts")
public class PostRestController {
	private final PostService postService;
	private final ModelMapper modelMapper;

	public PostRestController(PostService postService, ModelMapper modelMapper) {
		this.postService = postService;
		this.modelMapper = modelMapper;
	}

	@PatchMapping ("/{id}")
	public ResponseEntity<PostDTO> updatePostUpVotes(@RequestBody UpdatedPostDTO updatedPostDTO, @PathVariable Long id) {
		try {
			final Post post = postService.getPost(id).orElseThrow(() -> new PostNotFoundException(id));
			postService.setPostUpvoteCount(id, updatedPostDTO.getUpVotes());
			post.setUpVotes(updatedPostDTO.getUpVotes());
			return ResponseEntity.ok(modelMapper.map(post, PostDTO.class));
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}

	}
}
