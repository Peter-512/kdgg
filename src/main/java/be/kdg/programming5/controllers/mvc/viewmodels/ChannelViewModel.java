package be.kdg.programming5.controllers.mvc.viewmodels;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ChannelViewModel {
	@NotBlank (message = "{viewmodel.channel_not_empty}")
	@Size (min = 3, max = 25, message = "{viewmodel.channel_size}")
	private String name;

	@NotBlank (message = "{viewmodel.description_not_empty}")
	@Size (min = 3, max = 150, message = "{viewmodel.description_size}")
	private String description;
}
