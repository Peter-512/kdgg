package be.kdg.programming5.repository;

import be.kdg.programming5.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	@Transactional
	@Modifying
	@Query ("update Post p set p.upVotes = ?1 where p.postID = ?2")
	void updateUpVotesByPostID(int upVotes, Long postID);

	long countByChannel_ChannelID(Long channelID);

	long countByUser_UserID(Long userID);
}
