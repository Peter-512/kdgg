package be.kdg.programming3.repository.channels;

import be.kdg.programming3.domain.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@Profile ("dev")
public class ChannelRepositoryH2DB implements ChannelRepository {
	private final Logger logger;
	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert channelInserter;

	public ChannelRepositoryH2DB(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		this.channelInserter = new SimpleJdbcInsert(jdbcTemplate)
				.withTableName("channels")
				.usingGeneratedKeyColumns("channel_id");
		logger = LoggerFactory.getLogger(this.getClass());
	}

	@Override
	public List<Channel> findAll() {
		return jdbcTemplate.query("SELECT * FROM channels",
				(rs, rowNum) -> new Channel(rs.getLong("channel_id"), rs.getString("channel_name"), rs.getString("description")));
	}

	@Override
	public Channel createChannel(Channel channel) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("channel_name", channel.getName());
		parameters.put("description", channel.getDescription());
		channel.setChannelID(channelInserter.executeAndReturnKey(parameters).longValue());
		return channel;
	}

	@Override
	public void updateChannel(Channel channel) {
		jdbcTemplate.update("UPDATE channels SET channel_name = ?, description= ? WHERE channel_id = ?",
				channel.getName(), channel.getDescription(), channel.getChannelID());
	}

	@Override
	public boolean deleteChannel(Channel channel) {
		return jdbcTemplate.update("DELETE FROM channels WHERE channel_id = ?", channel.getChannelID()) != 0;
	}

	@Override
	public Optional<Channel> findById(Long id) {
		return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM channels WHERE channel_id = ?",
				(rs, rowNum) -> new Channel(rs.getString("channel_name"), rs.getString("description")), id));
	}

	@Override
	public long countByChannelID(Long channelID) {
		return findById(channelID).orElseThrow().getPosts().size();
	}
}
