package pl.com.muca.server.dao.poll;

import static pl.com.muca.server.entity.PollState.Filled;
import static pl.com.muca.server.entity.PollState.New;

import com.google.common.collect.ImmutableList;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import pl.com.muca.server.dao.question.QuestionDaoImpl;
import pl.com.muca.server.dao.user.UserDao;
import pl.com.muca.server.dao.user.UserDaoImpl;
import pl.com.muca.server.dao.useranswer.UserAnswerDao;
import pl.com.muca.server.dao.useranswer.UserAnswerDaoImpl;
import pl.com.muca.server.entity.Answer;
import pl.com.muca.server.entity.Poll;
import pl.com.muca.server.entity.PollState;
import pl.com.muca.server.entity.Question;

/**
 * Implementation of {@link pl.com.muca.server.dao.poll.PollDao}.
 */
@Repository
public class PollDaoImpl implements PollDao {
  private final UserDao userDao;
  private final UserAnswerDao userAnswerDao;
  private final QuestionDaoImpl questionDao;
  private static final String UPDATE_SQL =
      "UPDATE poll " + "SET name=:name, owner_user_id=:owner_user_id " + "WHERE poll_id=:poll_id";

  private final NamedParameterJdbcTemplate template;

  public PollDaoImpl(NamedParameterJdbcTemplate template) {
    this.template = template;
    this.userDao = new UserDaoImpl(template);
    this.questionDao = new QuestionDaoImpl(template);
    this.userAnswerDao = new UserAnswerDaoImpl(template, this);
  }

  @Override
  public List<Poll> findAllPolls(String token) throws Exception {
    ImmutableList<Poll> polls =
        ImmutableList.copyOf(template.query("SELECT * FROM poll", new PollRowMapper()));
    for (Poll poll : polls) {
      poll.setState(getPollState(poll.getPollId(), token));
    }
    return polls;
  }

  @Override
  public PollState getPollState(int pollId, String token) throws Exception {
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
  public ImmutableList<Poll> findAllMyPolls(String token) {
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
    int latestQuestionId = this.questionDao.getLatestQuestionId();
    int latestAnswerId = this.userAnswerDao.getLatestAnswerId();

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
    this.questionDao.insertQuestionTableData(poll);
    this.userAnswerDao.insertAnswerTableData(poll);
  }

  @Override
  public Integer getLatestPollId() {
    Integer latestPollId;
    final String latestPollIdSql = "SELECT MAX(poll.poll_id) " + "FROM poll;";
    latestPollId =
        Optional.ofNullable(
                template.queryForObject(
                    latestPollIdSql, new MapSqlParameterSource(), Integer.class))
            .orElse(0);
    return latestPollId;
  }

  @Override
  public void insertPollTableData(Poll poll) {
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

  @Override
  public Poll getPollDetails(int pollId, String token) throws Exception {
    Poll poll = new Poll();
    poll.setPollId(pollId);
    poll.setName(getPollName(pollId));
    poll.setQuestions(this.questionDao.getQuestions(pollId));
    for (Question question : poll.getQuestions()) {
      question.setAnswers(this.userAnswerDao.getAnswersCounterForPollOwner(question.getQuestionId(), token));
    }
    return poll;
  }

  @Override
  public String getPollName(int pollId) {
    String sql = "SELECT poll.name FROM poll WHERE poll_id = :PollId;";
    MapSqlParameterSource mapSqlParameterSource =
        new MapSqlParameterSource().addValue("PollId", pollId);
    return template.queryForObject(sql, mapSqlParameterSource, String.class);
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
