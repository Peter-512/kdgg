package be.kdg.programming3.view;

import be.kdg.programming3.domain.Channel;
import be.kdg.programming3.domain.Post;
import be.kdg.programming3.domain.User;
import be.kdg.programming3.service.ChatService;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Presenter {
	private final Scanner scanner;
	private final ChatService chatService;

	public Presenter(ChatService chatService) {
		this.chatService = chatService;
		scanner = new Scanner(System.in);
	}

	public int handleChoice() throws RuntimeException {
		final int input;
		try {
			input = scanner.nextInt();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			scanner.nextLine();
		}
		return input;
	}

	public int getChannelIndex() throws RuntimeException {
		System.out.println("0. (Cancel)");
		int input;
		AtomicInteger i = new AtomicInteger(1);
		chatService.getChannels()
		           .forEach(channel -> System.out.printf("%d. %s\n", i.getAndIncrement(), channel.getName()));
		System.out.printf("Choose a channel (1-%d): ", chatService.getChannels().size());
		try {
			input = handleChoice();
		} catch (RuntimeException e) {
			throw new RuntimeException();
		}
		return input - 1;
	}

	public List<Post> getPosts() {
		chatService.getChannels().stream().map(Channel::getName).forEach(System.out::println);
		System.out.println("Choose a channel by name:");
		System.out.print("-> ");
		final String channel = scanner.nextLine();

		List<Channel> channels = chatService.getChannels().stream().filter(c -> c.getName().contains(channel)).toList();

		List<User> users = chatService.getChannels()
		                              .stream()
		                              .filter(c -> c.getName().contains(channel))
		                              .map(Channel::getUsers).flatMap(List::stream).toList();

		users.forEach(u -> System.out.println(u.getName()));

		System.out.println("Choose a user by name:");
		System.out.print("-> ");
		final String user = scanner.nextLine();

		List<String> usernames = users.stream().map(User::getName).filter(name -> name.contains(user)).toList();

		System.out.println("Sort by upvotes? (ASC for ascending DESC for descending)");
		final String sorting = scanner.nextLine();
		final List<Post> posts = channels.stream()
		                                 .map(Channel::getPosts)
		                                 .flatMap(List::stream)
		                                 .filter(post -> usernames.contains(post.getUser().getName())).toList();
		if ("ASC".equals(sorting)) {
			return posts.stream()
			            .sorted(Comparator.comparingInt(Post::getUpVotes)).toList();
		} else if ("DESC".equals(sorting)) {
			return posts.stream()
			            .sorted(Comparator.comparingInt(Post::getUpVotes).reversed()).toList();
		} else {
			return posts;
		}
	}
}
