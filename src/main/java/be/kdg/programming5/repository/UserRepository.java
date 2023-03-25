package be.kdg.programming5.repository;

import be.kdg.programming5.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	@Modifying
	long deleteByName(String name);

	@Query ("select u from User u left join fetch u.channels where u.name = :user")
	User findUserWithChannelsByName(String user);

	boolean existsByName(String name);

	User findUserByName(String name);
}
