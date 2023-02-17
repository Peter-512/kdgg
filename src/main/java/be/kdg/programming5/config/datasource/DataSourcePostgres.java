package be.kdg.programming5.config.datasource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
@Profile ("prod")
public class DataSourcePostgres implements DataSourceConfig {
	@Value ("${POSTGRES_PASSWORD}")
	private String password;

	@Bean
	@Override
	public DataSource setup() {
		return DataSourceBuilder.create()
		                        .driverClassName("org.postgresql.Driver")
		                        .username("postgres")
		                        .password(password)
		                        .url("jdbc:postgresql://localhost:5432/kdGG")
		                        .build();
	}
}
