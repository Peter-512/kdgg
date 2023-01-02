package be.kdg.programming3.database;

import be.kdg.programming3.domain.Channel;
import be.kdg.programming3.domain.Post;
import be.kdg.programming3.domain.Role;
import be.kdg.programming3.domain.User;
import be.kdg.programming3.repository.channels.ChannelRepository;
import be.kdg.programming3.repository.users.UserRepository;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@Component
@Profile ("list")
public class ListDataBaseSeeder implements CommandLineRunner {
	private static final int INITIAL_CHANNELS = 5;
	private static final int INITIAL_USERS = 25;
	private static final int MIN_INITIAL_POSTS = 20;
	private static final int MAX_INITIAL_POSTS = 100;
	private static final int PERCENT = 60;
	private final Faker faker;
	private final ChannelRepository channelRepository;
	private final UserRepository userRepository;

	@Autowired
	public ListDataBaseSeeder(ChannelRepository channelRepository, UserRepository userRepository) {
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
		channelRepository.createChannel(new Channel("DuckiesGang", "The coolest gang in town, no spaghett allowed!"));
		final List<Channel> channels = Stream.generate(() -> new Channel(faker.starTrek().character(), faker.yoda()
		                                                                                                    .quote()))
		                                     .limit(INITIAL_CHANNELS)
		                                     .toList();
		channels.forEach(channelRepository::createChannel);
	}

	private void seedUsers() {
		final User peter = new User("peter.buschenreiter", LocalDate.of(1992, 11, 19), Role.Admin);
		userRepository.createUser(peter);
		channelRepository.findAll().stream().filter(channel -> randomizer(PERCENT)).forEach(peter::joinChannel);

		final List<User> users = Stream.generate(() -> {
			final User user = new User(faker.name().username(), LocalDate.ofInstant(faker.date()
			                                                                             .birthday()
			                                                                             .toInstant(), ZoneId.systemDefault()), Role.randomRole());
			channelRepository.findAll().stream().filter(channel -> randomizer(PERCENT)).forEach(user::joinChannel);
			return user;
		}).limit(INITIAL_USERS).toList();
		users.forEach(userRepository::createUser);
	}

	private void seedPosts() {
		channelRepository.findAll().forEach(channel -> {
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
			channel.setUpVotesRatio(channel.calculateUpvoteRatio());
		});
	}

	private boolean randomizer(int percent) {
		double cutoff = (double) percent / 100;
		return Math.random() < cutoff;
	}
}
