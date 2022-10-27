package be.kdg.programming3.repository;

import be.kdg.programming3.domain.Channel;
import be.kdg.programming3.domain.Post;
import be.kdg.programming3.domain.Role;
import be.kdg.programming3.domain.User;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@Component
public class DataBaseSeeder implements CommandLineRunner {
	private static final int INITIAL_CHANNELS = 5;
	private static final int INITIAL_USERS = 25;
	private static final int MIN_INITIAL_POSTS = 20;
	private static final int MAX_INITIAL_POSTS = 100;
	private final int PERCENT = 60;
	private final Faker faker;
	private final ChannelRepository channelRepository;
	private final UserRepository userRepository;

	@Autowired
	public DataBaseSeeder(ChannelRepository channelRepository, UserRepository userRepository) {
		this.channelRepository = channelRepository;
		this.userRepository = userRepository;
		faker = new Faker();
	}

	@Override
	public void run(String... args) {
		seed();
	}

	private void seed() {
		seedChannels();
		seedUsers();
		seedPosts();
	}

	private void seedChannels() {
		channelRepository.readChannels()
		                 .add(new Channel("DuckiesGang", "The coolest gang in town, no spaghett allowed!"));
		channelRepository.readChannels()
		                 .addAll(Stream.generate(() -> new Channel(faker.starTrek().character(), faker.yoda().quote()))
		                               .limit(INITIAL_CHANNELS)
		                               .toList());
	}

	private void seedUsers() {
		final User peter = new User("peter.buschenreiter", LocalDate.of(1992, 11, 19), Role.Admin);
		userRepository.readUsers().add(peter);
		channelRepository.readChannels().stream().filter(channel -> randomizer(PERCENT)).forEach(peter::joinChannel);

		userRepository.readUsers().addAll(Stream.generate(() -> {
			final User user = new User(faker.name().username(), LocalDate.ofInstant(faker.date()
			                                                                             .birthday()
			                                                                             .toInstant(), ZoneId.systemDefault()), Role.randomRole());
			channelRepository.readChannels().stream().filter(channel -> randomizer(PERCENT)).forEach(user::joinChannel);
			return user;
		}).limit(INITIAL_USERS).toList());
	}

	private void seedPosts() {
		channelRepository.readChannels().forEach(channel -> {
			final User[] userArr = channel.getUsers().toArray(new User[0]);
			List<Post> posts = Stream.generate(() -> new Post(
					                         faker.options().option(userArr),
					                         channel,
					                         faker.elderScrolls().quote(),
					                         faker.number().numberBetween(-20, 200),
					                         LocalDate.ofInstant(faker.date()
					                                                  .past(730, TimeUnit.DAYS)
					                                                  .toInstant(), ZoneId.systemDefault())))
			                         .limit(faker.number().numberBetween(MIN_INITIAL_POSTS, MAX_INITIAL_POSTS))
			                         .toList();
			channel.getPosts()
			       .addAll(posts.stream()
			                    .sorted(Comparator.comparing(Post::getDate))
			                    .toList());
			channel.calculateUpvoteRatio();
		});
	}

	private boolean randomizer(int percent) {
		double cutoff = (double) percent / 100;
		return Math.random() < cutoff;
	}
}
