package pl.com.muca.server.dao.poll;

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
import pl.com.muca.server.dao.user.UserDao;
import pl.com.muca.server.dao.user.UserDaoImpl;
import pl.com.muca.server.dao.useranswer.UserAnswerDao;
import pl.com.muca.server.dao.useranswer.UserAnswerDaoImpl;
import pl.com.muca.server.entity.Answer;
import pl.com.muca.server.entity.Poll;
import pl.com.muca.server.entity.PollState;
import pl.com.muca.server.entity.Question;
import pl.com.muca.server.mapper.PollRowMapper;
import pl.com.muca.server.mapper.QuestionRowMapper;

@Repository
public class PollDaoImpl implements PollDao {
  private final UserDao userDao;
  private final UserAnswerDao userAnswerDao;
  private static final String UPDATE_SQL =
      "UPDATE poll " + "SET name=:name, owner_user_id=:owner_user_id " + "WHERE poll_id=:poll_id";

  private final NamedParameterJdbcTemplate template;

  public PollDaoImpl(NamedParameterJdbcTemplate template) {
    this.template = template;
    this.userDao = new UserDaoImpl(template);
    this.userAnswerDao = new UserAnswerDaoImpl(template, this);
  }

  @Override
  public List<Poll> findAll(String token) throws Exception {
    ImmutableList<Poll> polls =
        ImmutableList.copyOf(template.query("SELECT * FROM poll", new PollRowMapper()));

    for (Poll poll : polls) {
      poll.setState(getPollState(poll.getPollId(), token));
    }
    return polls;
  }

  // TODO (Damian Muca): 6/18/20 make public, put into PollDao interface.
  private PollState getPollState(int pollId, String token) throws Exception {
    String userIdHash = this.userDao.getUserHashIdFromToken(token);
    String countUserAnswersToPollSql =
        "SELECT COUNT(*) AS howManyAnswers "
            + "FROM useranswer "
            + "INNER JOIN question "
            + "           ON useranswer.question_id=question.question_id "
            + "INNER JOIN session "
            + "           ON session.access_token=:SessionToken "
            + "WHERE useranswer.user_id_hash=:UserIdHash AND question.poll_id=:PollId; ";

    SqlParameterSource namedParameters =
        new MapSqlParameterSource()
            .addValue("PollId", pollId)
            .addValue("SessionToken", UUID.fromString(token))
            .addValue("UserIdHash", userIdHash);
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
    int latestPollId = getLatestPollId();
    int latestQuestionId = getLatestQuestionId();
    int latestAnswerId = getLatestAnswerId();

    poll.setOwnerUserId(this.userDao.getUserId(token));
    poll.setPollId(++latestPollId);
    for (int i = 0; i < poll.getQuestions().length; ++i) {
      Question question = poll.getQuestions()[i];
      question.setPollId(poll.getPollId());
      question.setQuestionId(++latestQuestionId);
      for (int j = 0; j < question.getAnswers().length; ++j) {
        Answer answer = question.getAnswers()[j];
        answer.setQuestionId(question.getQuestionId());
        answer.setAnswerId(++latestAnswerId);
      }
    }

    insertPollTableData(poll);
    insertQuestionTableData(poll);
    insertAnswerTableData(poll);
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

  // TODO (Damian Muca): 6/18/20 move to QuestionDao.
  private Integer getLatestQuestionId() {
    final String latestQuestionIdSql = "SELECT MAX(question.question_id) " + "FROM question;";
    return Optional.ofNullable(
            template.queryForObject(
                latestQuestionIdSql, new MapSqlParameterSource(), Integer.class))
        .orElse(0);
  }

  // TODO (Damian Muca): 6/18/20 move to AnswerDao.
  private Integer getLatestAnswerId() {
    final String latestAnswerIdSql = "SELECT MAX(answer.answer_id) " + "FROM answer;";
    return Optional.ofNullable(
            template.queryForObject(latestAnswerIdSql, new MapSqlParameterSource(), Integer.class))
        .orElse(0);
  }

  // TODO (Damian Muca): 6/18/20 make public, put into interface.
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

  // TODO (Damian Muca): 6/18/20 make public, put into QuestionDao.
  private void insertQuestionTableData(Poll poll) {
    final String sql =
        "INSERT INTO question(question_id, poll_id, content) "
            + "VALUES (:question_id, :poll_id, :content)";

    for (Question question : poll.getQuestions()) {
      SqlParameterSource param =
          new MapSqlParameterSource()
              .addValue("question_id", question.getQuestionId())
              .addValue("poll_id", question.getPollId())
              .addValue("content", question.getTitle());
      template.update(sql, param);
    }
  }

  // TODO (Damian Muca): 6/18/20 make public, put into AnswerDao.
  private void insertAnswerTableData(Poll poll) {
    int latestAnswerId = getLatestAnswerId();
    final String sql =
        "INSERT INTO answer(answer_id, question_id, content) "
            + "VALUES (:answer_id, :question_id, :content)";

    for (Question question : poll.getQuestions()) {
      for (Answer answer : question.getAnswers()) {
        SqlParameterSource param =
            new MapSqlParameterSource()
                .addValue("answer_id", answer.getAnswerId())
                .addValue("question_id", answer.getQuestionId())
                .addValue("content", answer.getContent());
        template.update(sql, param);
      }
    }
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

  @Override
  public Poll getPollDetails(int pollId, String token) throws Exception {
    Poll poll = new Poll();
    poll.setPollId(pollId);
    poll.setName(getPollName(pollId));
    poll.setQuestions(getQuestions(pollId));
    for (Question question : poll.getQuestions()) {
      question.setAnswers(this.userAnswerDao.getAnswers(question.getQuestionId(), token));
    }
    return poll;
  }

  private String getPollName(int pollId) {
    String sql = "SELECT poll.name FROM poll WHERE poll_id = :PollId;";
    MapSqlParameterSource mapSqlParameterSource =
        new MapSqlParameterSource().addValue("PollId", pollId);
    return template.queryForObject(sql, mapSqlParameterSource, String.class);
  }

  private Question[] getQuestions(int pollId) {
    String sql =
        "SELECT question.question_id, question.poll_id, question.content "
            + "FROM question "
            + "WHERE question.poll_id = :PollId;";
    SqlParameterSource questionParameters = new MapSqlParameterSource().addValue("PollId", pollId);
    return template
        .query(sql, questionParameters, new QuestionRowMapper())
        .toArray(Question[]::new);
  }

  @Override
  public int getPollId(int questionId) throws SQLException {
    final String getPollIdSql =
        "SELECT question.poll_id  FROM question  WHERE question_id = :QuestionId";
    SqlParameterSource questionIdParam =
        new MapSqlParameterSource().addValue("QuestionId", questionId);
    Optional<Integer> pollId =
        Optional.ofNullable(template.queryForObject(getPollIdSql, questionIdParam, Integer.class));

    if (pollId.isEmpty()) {
      throw new SQLException("Couldn't find poll_id for question_id: " + questionId);
    }
    return pollId.get();
  }
}
