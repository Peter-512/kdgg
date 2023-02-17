package be.kdg.programming5.repository;

import be.kdg.programming5.domain.User;
import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile ("prod")
public interface UserRepository extends CrudRepository<User, Long> {

}
