package be.kdg.programming5.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class ConsoleJsonWriter implements JsonWriter {
	private final Logger logger;
	private final Gson gson;

	@Autowired
	public ConsoleJsonWriter() {
		gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
		                        .setPrettyPrinting()
		                        .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
		                        .create();
		this.logger = LoggerFactory.getLogger(ConsoleJsonWriter.class);
	}

	public <T> byte[] getJsonBytes(List<T> list) {
		logger.info("Converting to JSON...");
		return gson.toJson(list).getBytes();
	}
}
