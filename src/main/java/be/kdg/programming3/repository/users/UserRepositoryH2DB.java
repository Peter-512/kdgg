package be.kdg.programming3.repository.users;

import be.kdg.programming3.domain.Role;
import be.kdg.programming3.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Primary
@Repository
@Profile ("dev")
public class UserRepositoryH2DB implements UserRepository {
	private final Logger logger;
	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert userInserter;

	public UserRepositoryH2DB(JdbcTemplate jdbcTemplate) {
		this.logger = LoggerFactory.getLogger(this.getClass());
		this.jdbcTemplate = jdbcTemplate;
		this.userInserter = new SimpleJdbcInsert(jdbcTemplate)
				.withTableName("users")
				.usingGeneratedKeyColumns("user_id");
	}

	@Override
	public List<User> findAll() {
		return jdbcTemplate.query("SELECT * FROM users", (rs, rowNum) -> new User(
				rs.getString("user_name"),
				rs.getDate("birthdate").toLocalDate(),
				Role.valueOf(rs.getString("role"))));
	}

	@Override
	public User createUser(User user) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("user_name", user.getName());
		parameters.put("birthdate", user.getBirthdate());
		parameters.put("role", user.getRole());
		user.setId(userInserter.executeAndReturnKey(parameters).intValue());
		return user;
	}

	@Override
	public void updateUser(User user) {
		jdbcTemplate.update("UPDATE users SET user_name = ?, birthdate = ?, role = ? WHERE user_id = ?",
				user.getName(), user.getBirthdate(), user.getRole(), user.getId());
	}

	@Override
	public boolean deleteUser(User user) {
		return jdbcTemplate.update("DELETE FROM users WHERE user_id = ?", user.getId()) != 0;
	}

}
