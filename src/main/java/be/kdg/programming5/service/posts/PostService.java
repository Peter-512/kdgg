package be.kdg.programming5.service.posts;

import be.kdg.programming5.model.Channel;
import be.kdg.programming5.model.Post;
import be.kdg.programming5.model.User;

import java.util.List;
import java.util.Optional;

public interface PostService {
	List<Post> getPosts();

	Optional<Post> getPost(Long id);

	Post addPost(User user, Channel channel, String content);

	void deletePost(Long id);

	void setPostUpvoteCount(Long postID, int upVotes);

	Post updatePost(Long id, String content);
}
