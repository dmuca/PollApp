package pl.com.muca.server.dao;

import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import pl.com.muca.server.entity.Poll;
import pl.com.muca.server.mapper.PollRowMapper;

@Repository
public class PollDaoImpl implements PollDao {

  private static final String UPDATE_SQL =
      "UPDATE poll "
          + "SET name=:name, owner_user_id=:owner_user_id "
          + "WHERE poll_id=:poll_id";

  private final NamedParameterJdbcTemplate template;

  public PollDaoImpl(NamedParameterJdbcTemplate template) {
    this.template = template;
  }

  @Override
  public List<Poll> findAll() {
    return template.query("SELECT * FROM poll", new PollRowMapper());
  }

  @Override
  public void insertPoll(Poll poll) {
    final String sql = "INSERT INTO poll(poll_id, owner_user_id, name) "
        + "VALUES (:poll_id, :owner_user_id, :name)";

    KeyHolder holder = new GeneratedKeyHolder();
    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("poll_id", poll.getPollId())
        .addValue("owner_user_id", poll.getOwnerUserId())
        .addValue("name", poll.getName());
    template.update(sql, param, holder);
  }

  @Override
  public void updatePoll(Poll poll) {
    KeyHolder holder = new GeneratedKeyHolder();
    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("poll_id", poll.getPollId())
        .addValue("owner_user_id", poll.getOwnerUserId())
        .addValue("name", poll.getName());
    template.update(UPDATE_SQL, param, holder);
  }

  @Override
  public void executeUpdatePoll(Poll poll) {
    Map<String, Object> map = new HashMap<>();
    map.put("poll_id", poll.getPollId());
    map.put("owner_user_id", poll.getOwnerUserId());
    map.put("name", poll.getName());

    template.execute(UPDATE_SQL, map,
        (PreparedStatementCallback<Object>) PreparedStatement::executeUpdate);
  }

  @Override
  public void deletePoll(Poll poll) {
    final String sql = "DELETE FROM poll WHERE poll_id=:poll_id";
    final Map<String, Object> map = new HashMap<>();
    map.put("poll_id", poll.getPollId());
    template.execute(sql, map,
        (PreparedStatementCallback<Object>) PreparedStatement::executeUpdate);
  }
}
