package be.kdg.programming3.repository.users;

import be.kdg.programming3.domain.User;
import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile ("prod")
public interface UserRepositoryHibernate extends CrudRepository<User, Long> {

}
