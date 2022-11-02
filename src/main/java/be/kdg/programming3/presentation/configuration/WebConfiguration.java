package be.kdg.programming3.presentation.configuration;

import be.kdg.programming3.presentation.converters.LocalDateToStringConverter;
import be.kdg.programming3.presentation.converters.StringToLocalDateConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {
	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new StringToLocalDateConverter());
		registry.addConverter(new LocalDateToStringConverter());
	}
}
