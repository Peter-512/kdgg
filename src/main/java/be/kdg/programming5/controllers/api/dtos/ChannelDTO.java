package be.kdg.programming5.controllers.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ChannelDTO {
	private long channelID;
	private String name;
	private String description;
}
