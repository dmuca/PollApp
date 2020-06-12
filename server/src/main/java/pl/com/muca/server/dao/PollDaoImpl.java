package pl.com.muca.server.dao;

import static pl.com.muca.server.entity.PollState.Filled;
import static pl.com.muca.server.entity.PollState.New;

import com.google.common.collect.ImmutableList;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
import pl.com.muca.server.entity.Question;
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

  private PollState getPollState(int pollId, String token) {
    String countUserAnswersToPollSql =
        "SELECT COUNT(*) AS howManyAnswers "
            + "FROM useranswer "
            + "INNER JOIN question "
            + "           ON useranswer.question_id=question.question_id "
            + "INNER JOIN session "
            + "           ON session.access_token=:SessionToken "
            // TODO (Damian Muca): 6/12/20 decrypt useranswer.user_id_hash.
            + "WHERE useranswer.user_id_hash=session.user_id AND question.poll_id=:PollId; ";

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
  public ImmutableList<Poll> findAllMine(String token) {
    SqlParameterSource namedParameters =
        new MapSqlParameterSource().addValue("SessionToken", UUID.fromString(token));
    return ImmutableList.copyOf(
        template.query(
            "SELECT * FROM poll "
                + "INNER JOIN session "
                + "ON session.access_token = :SessionToken "
                + "WHERE poll.owner_user_id = session.user_id;",
            namedParameters,
            new PollRowMapper()));
  }

  @Override
  public void insertPoll(Poll poll, String token) throws SQLException {
    poll.setPollId(getLatestPollId() + 1);
    poll.setOwnerUserId(getUserId(token));

    insertPollTableData(poll);
    insertQuestionTableData(poll);
  }

  private Integer getLatestPollId() {
    Integer latestPollId;
    final String latestPollIdSql = "SELECT MAX(poll.poll_id) " + "FROM poll;";
    latestPollId =
        Optional.ofNullable(
                template.queryForObject(
                    latestPollIdSql, new MapSqlParameterSource(), Integer.class))
            .orElse(0);
    return latestPollId;
  }

  private int getUserId(String token) throws SQLException {
    final String requestorUserIdSql =
        "SELECT appuser.user_id FROM appuser "
            + "INNER JOIN session on appuser.user_id = session.user_id "
            + "WHERE session.access_token = :SessionToken;";
    SqlParameterSource sessionTokenParam =
        new MapSqlParameterSource().addValue("SessionToken", UUID.fromString(token));
    Optional<Integer> userIdOptional =
        Optional.ofNullable(
            template.queryForObject(requestorUserIdSql, sessionTokenParam, Integer.class));

    if (userIdOptional.isEmpty()) {
      throw new SQLException("Couldn't find user id hash base on its session token");
    }
    return userIdOptional.get();
  }

  private void insertPollTableData(Poll poll) {
    final String sql =
        "INSERT INTO poll(poll_id, owner_user_id, name) "
            + "VALUES (:poll_id,:owner_user_id, :name)";
    SqlParameterSource param =
        new MapSqlParameterSource()
            .addValue("poll_id", poll.getPollId())
            .addValue("owner_user_id", poll.getOwnerUserId())
            .addValue("name", poll.getName().trim());
    template.update(sql, param);
  }

  private void insertQuestionTableData(Poll poll) {
    int latestQuestionId = getLatestQuestionId();
    final String sql =
        "INSERT INTO question(question_id, poll_id, content) "
            + "VALUES (:question_id, :poll_id, :content)";

    for (Question question : poll.getQuestions()) {
      SqlParameterSource param =
          new MapSqlParameterSource()
              .addValue("question_id", ++latestQuestionId)
              .addValue("poll_id", poll.getPollId())
              .addValue("content", question.getTitle());
      template.update(sql, param);
    }
  }

  private Integer getLatestQuestionId() {
    final String latestPollIdSql = "SELECT MAX(question.question_id) " + "FROM question;";
    return Optional.ofNullable(
            template.queryForObject(latestPollIdSql, new MapSqlParameterSource(), Integer.class))
        .orElse(0);
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
