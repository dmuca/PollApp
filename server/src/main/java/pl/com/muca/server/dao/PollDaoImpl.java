package pl.com.muca.server.dao;

import static pl.com.muca.server.entity.PollState.Filled;
import static pl.com.muca.server.entity.PollState.New;

import com.google.common.collect.ImmutableList;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import pl.com.muca.server.entity.Poll;
import pl.com.muca.server.entity.PollState;
import pl.com.muca.server.mapper.PollRowMapper;

@Repository
public class PollDaoImpl implements PollDao {

  private static final String UPDATE_SQL =
      "UPDATE poll " + "SET name=:name, owner_user_id=:owner_user_id " + "WHERE poll_id=:poll_id";

  private final NamedParameterJdbcTemplate template;

  public PollDaoImpl(NamedParameterJdbcTemplate template) {
    this.template = template;
  }

  @Override
  public List<Poll> findAll(String token) {
    ImmutableList<Poll> polls =
        ImmutableList.copyOf(template.query("SELECT * FROM poll", new PollRowMapper()));

    polls.forEach(poll -> poll.setState(getPollState(poll.getPollId(), token)));
    return polls;
  }

  @Override
  public ImmutableList<Poll> findAllMine(String token) {
    SqlParameterSource namedParameters =
        new MapSqlParameterSource().addValue("SessionToken", UUID.fromString(token));
    return ImmutableList.copyOf(
        template.query(
            "SELECT * FROM poll "
                + "INNER JOIN session "
                + "ON session.access_token = :SessionToken "
                + "WHERE poll.owner_user_id = session.user_id_hash;",
            namedParameters,
            new PollRowMapper()));
  }

  private PollState getPollState(int pollId, String token) {
    String countUserAnswersToPollSql =
        "SELECT COUNT(*) AS howManyAnswers "
            + "FROM useranswer "
            + "INNER JOIN question "
            + "           ON useranswer.question_id=question.question_id "
            + "INNER JOIN session "
            + "           ON session.access_token=:SessionToken "
            + "WHERE useranswer.user_id=session.user_id_hash AND question.poll_id=:PollId; ";

    SqlParameterSource namedParameters =
        new MapSqlParameterSource()
            .addValue("PollId", pollId)
            .addValue("SessionToken", UUID.fromString(token));
    Optional<Integer> answersToPoll =
        Optional.ofNullable(
            template.queryForObject(countUserAnswersToPollSql, namedParameters, Integer.class));
    return answersToPoll.filter(integer -> integer > 0).map(integer -> Filled).orElse(New);
  }

  @Override
  public void insertPoll(Poll poll) {
    final String sql =
        "INSERT INTO poll(poll_id, owner_user_id, name) "
            + "VALUES (:poll_id, :owner_user_id, :name)";

    KeyHolder holder = new GeneratedKeyHolder();
    SqlParameterSource param =
        new MapSqlParameterSource()
            .addValue("poll_id", poll.getPollId())
            .addValue("owner_user_id", poll.getOwnerUserId())
            .addValue("name", poll.getName().trim());
    template.update(sql, param, holder);
  }

  @Override
  public void updatePoll(Poll poll) {
    KeyHolder holder = new GeneratedKeyHolder();
    SqlParameterSource param =
        new MapSqlParameterSource()
            .addValue("poll_id", poll.getPollId())
            .addValue("owner_user_id", poll.getOwnerUserId())
            .addValue("name", poll.getName().trim());
    template.update(UPDATE_SQL, param, holder);
  }

  @Override
  public void executeUpdatePoll(Poll poll) {
    Map<String, Object> map = new HashMap<>();
    map.put("poll_id", poll.getPollId());
    map.put("owner_user_id", poll.getOwnerUserId());
    map.put("name", poll.getName().trim());

    template.execute(
        UPDATE_SQL, map, (PreparedStatementCallback<Object>) PreparedStatement::executeUpdate);
  }

  @Override
  public void deletePoll(Poll poll) {
    final String sql = "DELETE FROM poll WHERE poll_id=:poll_id";
    final Map<String, Object> map = new HashMap<>();
    map.put("poll_id", poll.getPollId());
    template.execute(
        sql, map, (PreparedStatementCallback<Object>) PreparedStatement::executeUpdate);
  }
}
