package be.kdg.programming3.presentation.viewmodel;

import be.kdg.programming3.domain.Role;
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
	private String name;

	@NotNull (message = "{viewmodel.birthdate_not_empty}")
	@Past (message = "{viewmodel.birthdate_past}")
	private LocalDate birthdate;

	@NotNull (message = "Role cannot be empty")
	private Role role;
}
