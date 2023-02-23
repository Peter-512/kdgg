package be.kdg.programming5.controllers.api.dtos;

import be.kdg.programming5.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdatedUserDTO {
	@NotBlank
	private String name;
	@NotNull
	private LocalDate birthdate;
	@NotNull
	private Role role;
}
