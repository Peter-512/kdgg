package be.kdg.programming3.presentation.viewmodel;

import be.kdg.programming3.domain.User;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PostViewModel {
	@NotBlank
	public String content;
	@NotNull
	public User user;
}
