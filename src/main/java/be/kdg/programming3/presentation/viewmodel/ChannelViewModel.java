package be.kdg.programming3.presentation.viewmodel;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ChannelViewModel {
	@NotBlank (message = "Channel name cannot be empty")
	@Size (min = 3, max = 25, message = "Channel name should be between 3 and 25 characters")
	@UniqueElements (message = "Channel name already taken")
	private String name;

	@NotBlank (message = "Channel description cannot be empty")
	@Size (min = 3, max = 150, message = "Channel description should be between 3 and 150 characters")
	private String description;
}
