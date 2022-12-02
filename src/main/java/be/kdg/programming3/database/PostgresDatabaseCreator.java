package be.kdg.programming3.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Profile ("prod")
public class PostgresDatabaseCreator implements DatabaseCreator {
	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public PostgresDatabaseCreator(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	@PostConstruct
	public void loadData() {
		jdbcTemplate.update("TRUNCATE TABLE posts CASCADE");
		jdbcTemplate.update("TRUNCATE TABLE users CASCADE");
		jdbcTemplate.update("TRUNCATE TABLE channels CASCADE");
		jdbcTemplate.update("DROP TYPE IF EXISTS ROLE");
		jdbcTemplate.update("CREATE TYPE ROLE AS ENUM ('User', 'Mod', 'Admin')");

		jdbcTemplate.update("""
				CREATE TABLE IF NOT EXISTS channels
				(
				    channel_id   BIGSERIAL
				        PRIMARY KEY,
				    description  VARCHAR(255) NOT NULL,
				    channel_name VARCHAR(255) NOT NULL
				);""");

		jdbcTemplate.update("""
				CREATE TABLE IF NOT EXISTS users
				(
				    user_id   BIGSERIAL
				        PRIMARY KEY,
				    birthdate DATE,
				    user_name VARCHAR(255),
				    role      VARCHAR(255)
				);""");

		jdbcTemplate.update("""
				CREATE TABLE IF NOT EXISTS user_channels
				(
				    channel_id BIGINT NOT NULL
				            REFERENCES channels(channel_id),
				    user_id    BIGINT NOT NULL
				            REFERENCES users(user_id)
				);""");

		jdbcTemplate.update("""
				CREATE TABLE IF NOT EXISTS posts
				(
				    post_id    BIGSERIAL
				        PRIMARY KEY,
				    content    VARCHAR(255) NOT NULL,
				    date       DATE         NOT NULL,
				    up_votes   INTEGER      NOT NULL,
				    channel_id BIGINT       NOT NULL
				            REFERENCES user_channels(channel_id),
				    user_id    BIGINT       NOT NULL
				            REFERENCES user_channels(user_id)
				);""");

		jdbcTemplate.update("""
				INSERT INTO users( user_name, birthdate, role )
				VALUES ( 'Peter', '1992-11-19', 'Admin' ),
				       ( 'Seif', '2003-10-12', 'Mod' ),
				       ( 'Filip', '2001-06-15', 'Mod' ),
				       ( 'Elina', '2003-04-15', 'User' );""");
		jdbcTemplate.update("""
				INSERT INTO channels ( channel_name, description )
				VALUES ( 'DuckiesGang', 'The coolest gang in town, no spaghett allowed!' ),
				       ( 'ACS', 'Applied Computer Science at KdG' );""");

		jdbcTemplate.update("INSERT INTO user_channels VALUES (1,1)");
	}
}
