package be.kdg.programming5.repository;

import be.kdg.programming5.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	User findUserByName(String name);
}
