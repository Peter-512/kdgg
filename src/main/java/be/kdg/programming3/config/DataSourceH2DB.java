package be.kdg.programming3.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
@Profile ("dev")
public class DataSourceH2DB implements DataSourceConfig {
	@Bean
	public DataSource setup() {
		return DataSourceBuilder.create()
		                        .driverClassName("org.h2.Driver")
		                        .url("jdbc:h2:file:./db/chatDB")
		                        .build();
	}
}
