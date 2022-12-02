package be.kdg.programming3.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Profile ("dev")
public class H2DatabaseCreator implements DatabaseCreator {
	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public H2DatabaseCreator(JdbcTemplate jdbcTemplate) {this.jdbcTemplate = jdbcTemplate;}

	@Override
	@PostConstruct
	public void loadData() {
		jdbcTemplate.update("DROP TABLE IF EXISTS users");
		jdbcTemplate.update("DROP TABLE IF EXISTS channels");
		jdbcTemplate.update("DROP TABLE IF EXISTS posts");
		jdbcTemplate.update("CREATE TYPE IF NOT EXISTS role AS ENUM ('User', 'Mod', 'Admin')");
		jdbcTemplate.update("CREATE TABLE IF NOT EXISTS users ( user_id INTEGER AUTO_INCREMENT PRIMARY KEY, user_name CHARACTER VARYING NOT NULL, birthdate DATE NOT NULL, role ROLE NOT NULL)");
		jdbcTemplate.update("CREATE TABLE IF NOT EXISTS channels (channel_id INTEGER AUTO_INCREMENT PRIMARY KEY, channel_name CHARACTER VARYING NOT NULL,description CHARACTER VARYING NOT NULL)");
		jdbcTemplate.update("CREATE TABLE IF NOT EXISTS posts (post_id INTEGER AUTO_INCREMENT PRIMARY KEY, content CHARACTER VARYING NOT NULL, upvotes INTEGER NOT NULL DEFAULT 0, date DATE NOT NULL DEFAULT NOW())");

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
	}
}
