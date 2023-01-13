package be.kdg.programming3.repository.channels;

import be.kdg.programming3.domain.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.List;
import java.util.Optional;

@Repository
@Profile ("em")
public class ChannelRepositoryEM implements ChannelRepository {
	@PersistenceUnit
	private final EntityManagerFactory entityManagerFactory;
	private final EntityManager em;

	@Autowired
	public ChannelRepositoryEM(EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
		this.em = entityManagerFactory.createEntityManager();
	}

	@Override
	public List<Channel> findAll() {
		return em.createQuery("SELECT c FROM Channel c", Channel.class).getResultList();
	}

	@Override
	public Channel createChannel(Channel channel) {
		em.getTransaction().begin();
		em.persist(channel);
		em.getTransaction().commit();
		return channel;
	}

	@Override
	public void updateChannel(Channel channel) {
		em.getTransaction().begin();
		em.merge(channel);
		em.getTransaction().commit();
	}

	@Override
	public boolean deleteChannel(Channel channel) {
		em.getTransaction().begin();
		em.remove(channel);
		em.getTransaction().commit();
		return true;
	}

	@Override
	public Optional<Channel> findById(Long id) {
		return Optional.of(em.find(Channel.class, id));
	}

	@Override
	public long countByChannelID(Long channelID) {
		return 0;
	}
}
