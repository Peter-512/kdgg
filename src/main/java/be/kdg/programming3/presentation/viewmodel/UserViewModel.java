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
	@NotBlank (message = "Username cannot be empty")
	@Size (min = 3, max = 25, message = "Username should be between 3 and 25 characters")
	private String name;

	@NotNull (message = "Birthday cannot be empty")
	@Past (message = "Birthday cannot be in the future")
	private LocalDate birthdate;

	@NotNull (message = "Role cannot be empty")
	private Role role;
}
