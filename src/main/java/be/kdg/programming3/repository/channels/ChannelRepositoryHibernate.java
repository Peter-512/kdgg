package be.kdg.programming3.repository.channels;

import be.kdg.programming3.domain.Channel;
import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile ("prod")
public interface ChannelRepositoryHibernate extends CrudRepository<Channel, Long> {
}
