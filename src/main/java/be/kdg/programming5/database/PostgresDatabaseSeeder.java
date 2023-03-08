package be.kdg.programming5.database;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
@Profile ("prod")
public class PostgresDatabaseSeeder implements DatabaseSeeder {
	private final JdbcTemplate jdbcTemplate;
	private final Faker faker = new Faker();

	@Autowired
	public PostgresDatabaseSeeder(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	private Date fakeDate() {
		return faker.date().past(100, TimeUnit.DAYS);
	}

	@Override
	@PostConstruct
	public void loadData() {
		jdbcTemplate.update("""
				INSERT INTO users( user_name, password, birthdate, role )
				VALUES ( 'Peter', '$2a$10$c9ATkiyNYfjHu/L.Abv8OeaAS/BVJpwb4stk.hDzPIUd0eZ5Mbo1C', '1992-11-19', 'Admin' ),
				       ( 'Seif', '$2a$10$c9ATkiyNYfjHu/L.Abv8OeaAS/BVJpwb4stk.hDzPIUd0eZ5Mbo1C', '2003-10-12', 'Mod' ),
				       ( 'Filip', '$2a$10$c9ATkiyNYfjHu/L.Abv8OeaAS/BVJpwb4stk.hDzPIUd0eZ5Mbo1C', '2001-06-15', 'Mod' ),
				       ( 'Elina', '$2a$10$c9ATkiyNYfjHu/L.Abv8OeaAS/BVJpwb4stk.hDzPIUd0eZ5Mbo1C', '2003-04-15', 'User' );""");
		jdbcTemplate.update("""
				INSERT INTO channels ( channel_name, description )
				VALUES ( 'DuckiesGang', 'The coolest gang in town, no spaghett allowed!' ),
				       ( 'ACS', 'Applied Computer Science at KdG' ),
					   ('Lonely', 'A channel for lonely people')""");

		jdbcTemplate.update("""
						INSERT INTO posts (content, posted_at, up_votes, channel_id, user_id)
						VALUES ('The first post by Peter in DuckiesGang', ?, 4, 1, 1),
								('The second post by Peter in DuckiesGang', ?, 4, 1, 1),
								('The third post by Peter in DuckiesGang', ?, 4, 1, 1),
								('The fourth post by Peter in DuckiesGang', ?, 4, 1, 1),
							   ('The first post by Seif in ACS', ?, 6, 2, 2),
							   ('skljfldjsf', ?, 69, 1, 4)""",
				fakeDate(), fakeDate(), fakeDate(), fakeDate(), fakeDate(), fakeDate());

		jdbcTemplate.update("INSERT INTO user_channels VALUES (1,1)");
	}
}
