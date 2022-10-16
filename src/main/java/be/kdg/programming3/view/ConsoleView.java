package be.kdg.programming3.view;

import be.kdg.programming3.domain.Post;
import be.kdg.programming3.domain.User;
import be.kdg.programming3.json.ConsoleJsonWriter;
import be.kdg.programming3.json.JsonWriter;
import be.kdg.programming3.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConsoleView implements View {
	private final ChatService chatService;
	private final Presenter presenter;
	private final JsonWriter jsonWriter = new ConsoleJsonWriter();

	@Autowired
	public ConsoleView(ChatService chatService) {
		this.chatService = chatService;
		presenter = new Presenter(chatService);
	}

	@Override
	public void show() {
		while (true) {
			String menu = """
					What would you like to do?
					==========================
					0) Quit
					1) Show all users
					2) Show all channels
					3) Show posts of a channel
					4) Show users of a channel
					5) Filter posts by user in a channel and sort on upVotes (leave empty for no selection)
					Choice (0-5):\040""";

			System.out.print(menu);
			int input;
			try {
				input = presenter.handleChoice();
			} catch (RuntimeException e) {
				System.out.println("Invalid input.");
				continue;
			}

			// TODO implement saving to JSON
			switch (input) {
				case 0 -> System.exit(0);
				case 1 -> chatService.getUsers()
				                     .stream()
				                     .map(user -> user.getName() + " " + user.getBirthdate())
				                     .forEach(System.out::println);
				case 2 -> chatService.getChannels().forEach(System.out::println);
				case 3 -> {
					int channelIndex;
					try {
						channelIndex = presenter.getChannelIndex();
					} catch (RuntimeException e) {
						System.out.println("Invalid input.");
						break;
					}
					if (channelIndex >= 0 && channelIndex < chatService.getChannels().size()) {
						final List<Post> posts = chatService.getChannels()
						                                    .get(channelIndex)
						                                    .getPosts();
						posts.forEach(System.out::println);
						jsonWriter.writePosts(posts);
					} else {System.out.println("Cancelling...");}
				}
				case 4 -> {
					int channelIndex;
					try {
						channelIndex = presenter.getChannelIndex();
					} catch (RuntimeException e) {
						System.out.println("Invalid input.");
						break;
					}
					if (channelIndex >= 0 && channelIndex < chatService.getChannels().size()) {
						final List<User> users = chatService.getChannels()
						                                    .get(channelIndex)
						                                    .getUsers();
						users.forEach(System.out::println);
						jsonWriter.writeUsers(users);
					} else {System.out.println("Cancelling...");}
				}
				case 5 -> {
					final List<Post> posts = presenter.getPosts();
					posts.forEach(System.out::println);
					jsonWriter.writePosts(posts);
				}
			}
		}
	}
}
