package be.kdg.programming3.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Profile ({"dev", "em"})
public class H2DatabaseSeeder implements DatabaseSeeder {
	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public H2DatabaseSeeder(JdbcTemplate jdbcTemplate) {this.jdbcTemplate = jdbcTemplate;}

	@Override
	@PostConstruct
	public void loadData() {
		jdbcTemplate.update("DROP TABLE IF EXISTS posts");
		jdbcTemplate.update("DROP TABLE IF EXISTS channels CASCADE");
		jdbcTemplate.update("DROP TABLE IF EXISTS users CASCADE");
		jdbcTemplate.update("DROP TABLE IF EXISTS user_channels CASCADE");
		jdbcTemplate.update("CREATE TABLE IF NOT EXISTS users ( user_id INTEGER AUTO_INCREMENT PRIMARY KEY, user_name CHARACTER VARYING NOT NULL, birthdate DATE NOT NULL, role ENUM('User', 'Mod', 'Admin') NOT NULL)");
		jdbcTemplate.update("CREATE TABLE IF NOT EXISTS channels (channel_id INTEGER AUTO_INCREMENT PRIMARY KEY, channel_name CHARACTER VARYING NOT NULL,description CHARACTER VARYING NOT NULL)");
		jdbcTemplate.update("CREATE TABLE IF NOT EXISTS posts (post_id INTEGER AUTO_INCREMENT PRIMARY KEY, content CHARACTER VARYING NOT NULL, channel_id INTEGER NOT NULL, user_id INTEGER NOT NULL, up_votes INTEGER NOT NULL DEFAULT 0, date DATE NOT NULL DEFAULT NOW(), FOREIGN KEY (channel_id) REFERENCES channels(channel_id), FOREIGN KEY (user_id) REFERENCES users(user_id))");
		jdbcTemplate.update("CREATE TABLE IF NOT EXISTS user_channels (user_id INTEGER NOT NULL, channel_id INTEGER NOT NULL, PRIMARY KEY (user_id, channel_id), FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE, FOREIGN KEY (channel_id) REFERENCES channels(channel_id) ON DELETE CASCADE)");

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
