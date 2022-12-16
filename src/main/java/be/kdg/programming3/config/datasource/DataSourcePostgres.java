package be.kdg.programming3.config.datasource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
@Profile ("prod")
public class DataSourcePostgres implements DataSourceConfig {
	@Bean
	@Override
	public DataSource setup() {
		return DataSourceBuilder.create()
		                        .driverClassName("org.postgresql.Driver")
		                        .username("postgres")
		                        .password("anubis512")
		                        .url("jdbc:postgresql://localhost:5432/kdGG")
		                        .build();
	}
}
