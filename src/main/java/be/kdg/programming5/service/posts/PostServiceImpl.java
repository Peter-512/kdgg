package be.kdg.programming5.service.posts;

import be.kdg.programming5.exceptions.PostNotFoundException;
import be.kdg.programming5.model.Channel;
import be.kdg.programming5.model.Post;
import be.kdg.programming5.model.User;
import be.kdg.programming5.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {
	private final PostRepository postRepository;

	public PostServiceImpl(PostRepository postRepository) {this.postRepository = postRepository;}

	@Override
	public List<Post> getPosts() {
		return postRepository.findAll();
	}

	@Override
	public Optional<Post> getPost(Long id) {
		return postRepository.findById(id);
	}

	@Override
	public Post addPost(User user, Channel channel, String content) {
		return postRepository.save(new Post(user, channel, content));
	}

	@Override
	public void deletePost(Long id) {
		postRepository.deleteById(id);
	}

	@Override
	public void setPostUpvoteCount(Long postID, int upVotes) {
		postRepository.updateUpVotesByPostID(upVotes, postID);
	}

	@Override
	public Post updatePost(Long id, String content) {
		final Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
		post.setContent(content);
		return postRepository.save(post);
	}
}
