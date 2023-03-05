package be.kdg.programming5.controllers.mvc.viewmodels;

import be.kdg.programming5.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PostViewModel {
	@NotBlank
	public String content;
	@NotNull
	public User user;
}
