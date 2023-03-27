package be.kdg.programming5.config;

import be.kdg.programming5.controllers.api.dtos.PostDTO;
import be.kdg.programming5.model.Post;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MappingConfiguration {
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.createTypeMap(Post.class, PostDTO.class)
		           .addMapping(source -> source.getUser().getUserID(), PostDTO::setUserID)
		           .addMapping(source -> source.getUser().getName(), PostDTO::setUsername);
		return modelMapper;
	}
}
