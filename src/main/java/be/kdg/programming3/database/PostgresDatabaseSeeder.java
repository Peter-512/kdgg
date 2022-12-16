package be.kdg.programming3.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Profile ("prod")
public class PostgresDatabaseSeeder implements DatabaseSeeder {
	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public PostgresDatabaseSeeder(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	@PostConstruct
	public void loadData() {
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

		jdbcTemplate.update("""
				INSERT INTO posts (content, date, up_votes, channel_id, user_id)
				VALUES ('The first post by Peter in DuckiesGang', NOW(), 4, 1, 1),
					   ('The first post by Seif in ACS', NOW(), 6, 2, 2)""");

		jdbcTemplate.update("INSERT INTO user_channels VALUES (1,1)");
	}
}
