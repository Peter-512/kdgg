package be.kdg.programming5.controllers.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NewChannelDTO {
	@NotBlank (message = "Name cannot be empty")
	@Size (min = 3, max = 25, message = "Name size must be between 3 and 25 characters")
	private String name;

	@NotBlank (message = "Description cannot be empty")
	@Size (min = 3, max = 150, message = "Description size must be between 3 and 150 characters")
	private String description;
}
