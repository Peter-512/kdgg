package be.kdg.programming5.controllers.api.dtos;

import be.kdg.programming5.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTO {
	private Long userID;
	private String name;
	private LocalDate birthdate;
	private Role role;
}
