package be.kdg.programming3.presentation.viewmodel;

import be.kdg.programming3.domain.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostViewModel {
	public String content;
	public User user;
}
