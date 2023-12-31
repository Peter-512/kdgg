package be.kdg.programming5.controllers.mvc.viewmodels;

import be.kdg.programming5.config.security.UniqueUsernameConstraint;
import be.kdg.programming5.model.Role;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
public class UserViewModel {
	@NotBlank (message = "{viewmodel.user_not_empty}")
	@Size (min = 3, max = 25, message = "{viewmodel.user_size}")
	@UniqueUsernameConstraint
	private String name;

	@NotNull (message = "{viewmodel.birthdate_not_empty}")
	@Past (message = "{viewmodel.birthdate_past}")
	private LocalDate birthdate;

	@NotNull (message = "Role cannot be empty")
	private Role role;
	@NotBlank (message = "Password cannot be empty")
	private String password;
}
