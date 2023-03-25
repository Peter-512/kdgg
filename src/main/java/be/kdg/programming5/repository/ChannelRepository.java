package be.kdg.programming5.repository;

import be.kdg.programming5.model.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, Long> {
	@Query ("select c from Channel c left join fetch c.users where c.name = :name")
	Channel findChannelWithUsersByName(String name);
}
