package be.kdg.programming5.controllers.mvc.viewmodels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserLoginViewModel {
	private String username;
	private String password;
}
