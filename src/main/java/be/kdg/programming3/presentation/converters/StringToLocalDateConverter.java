package be.kdg.programming3.presentation.converters;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class StringToLocalDateConverter implements Converter<String, LocalDate> {
	@Override
	public LocalDate convert(String source) {
		if (source.isBlank()) return null;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return LocalDate.parse(source, formatter);
	}
}
