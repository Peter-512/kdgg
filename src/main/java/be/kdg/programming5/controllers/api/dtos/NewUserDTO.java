package be.kdg.programming5.controllers.api.dtos;

import be.kdg.programming5.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NewUserDTO {
	@NotBlank (message = "Message cannot be empty")
	@Size (min = 3, max = 25, message = "Message size must be between 3 and 25 characters")
	private String name;

	@NotNull (message = "Birthdate cannot be empty")
	@Past (message = "Birthdate must be in the past")
	private LocalDate birthdate;

	@NotNull (message = "Role cannot be empty")
	private Role role;
	@NotBlank (message = "Password cannot be empty")
	private String password;

}
