package be.kdg.programming3.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class LogTestRunner implements CommandLineRunner {
	private final Logger logger = LoggerFactory.getLogger(LogTestRunner.class);

	@Override
	public void run(String... args) {
		logger.trace("A TRACE Message");
		logger.debug("A DEBUG Message");
		logger.info("An INFO Message");
		logger.warn("A WARN Message");
		logger.error("An ERROR Message");
	}
}
