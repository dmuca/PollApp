package pl.com.muca.server.dao;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Optional;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import pl.com.muca.server.entity.Answer;
import pl.com.muca.server.entity.UserAnswer;
import pl.com.muca.server.mapper.AnswerRowMapper;

@Repository
public class UserAnswerDaoImpl implements UserAnswerDao {
  private final UserDao userDao;
  private final PollDao pollDao;
  private final QuestionDao questionDao;
  private final NamedParameterJdbcTemplate template;

  public UserAnswerDaoImpl(NamedParameterJdbcTemplate template, PollDaoImpl pollDao) {
    this.template = template;
    this.userDao = new UserDaoImpl(template);
    this.questionDao = new QuestionDaoImpl(template);
    this.pollDao = pollDao;
  }

  @Override
  public int saveUserAnswers(UserAnswer[] userAnswers, String token) throws Exception {
    String userIdHash = this.userDao.getUserHashIdFromToken(token);
    Arrays.stream(userAnswers).forEach(userAnswer -> insertToUserAnswer(userIdHash, userAnswer));

    int pollId = this.pollDao.getPollId(userAnswers[0].getQuestionId());
    int userId = this.userDao.getUserId(token);

    int userAnswersHashCode = generateUserAnswersHashCode(userAnswers);
    insertToUserAnswerValidator(userId, pollId, userAnswersHashCode);
    System.out.println("1. Poll id: " + pollId);
    System.out.println("2. userAnswersHashCode: " + userAnswersHashCode);
    return userAnswersHashCode;
  }

  private int generateUserAnswersHashCode(UserAnswer[] userAnswers) {
    return Math.abs(
        Arrays.stream(userAnswers)
            .map(Object::hashCode)
            .mapToInt(Integer::intValue)
            .reduce(Integer::sum)
            .orElse(0));
  }

  @Override
  public void insertToUserAnswer(String userIdHash, UserAnswer userAnswer) {
    final String sql =
        "INSERT INTO useranswer(user_id_hash, question_id, answer_chosen) "
            + "VALUES (:UserIdHash, :QuestionId, :AnswerChosen)";
    SqlParameterSource param =
        new MapSqlParameterSource()
            .addValue("UserIdHash", userIdHash)
            .addValue("QuestionId", userAnswer.getQuestionId())
            .addValue("AnswerChosen", userAnswer.getAnswerChosen());
    template.update(sql, param);
  }

  @Override
  public Answer[] getAnswers(int questionId, String token) throws Exception {
    String sql =
        "SELECT answer.answer_id, answer.question_id, answer.content "
            + "FROM answer "
            + "WHERE answer.question_id = :QuestionId;";
    SqlParameterSource answerParameters =
        new MapSqlParameterSource().addValue("QuestionId", questionId);
    Answer[] answers =
        template.query(sql, answerParameters, new AnswerRowMapper()).toArray(Answer[]::new);

    boolean isUserOwnerOfTheQuestion =
        this.questionDao.isUserACreatorOfTheQuestion(token, questionId);

    for (Answer answer : answers) {
      answer.setMarkedByUser(isAnswerMarkedByUser(answer.getAnswerId(), token));
      if (isUserOwnerOfTheQuestion) {
        answer.setAnswersCounter(countAnswers(answer.getAnswerId()));
      }
    }
    return answers;
  }

  @Override
  public boolean isAnswerMarkedByUser(int answerId, String token) throws Exception {
    String userIdHash = this.userDao.getUserHashIdFromToken(token);
    String sql =
        "SELECT COUNT(*) AS markedCounter "
            + "FROM useranswer "
            + "INNER JOIN answer "
            + "ON useranswer.answer_chosen = :AnswerId "
            + "WHERE useranswer.user_id_hash = :UserIdHash";
    SqlParameterSource sqlParameterSource =
        new MapSqlParameterSource()
            .addValue("AnswerId", answerId)
            .addValue("UserIdHash", userIdHash);
    Integer value = template.queryForObject(sql, sqlParameterSource, Integer.class);
    return Optional.ofNullable(value).orElse(0) > 0;
  }

  @Override
  public int countAnswers(int answerId) {
    String sql = "SELECT COUNT(*) FROM useranswer WHERE answer_chosen = :AnswerId;";
    SqlParameterSource sqlParameterSource =
        new MapSqlParameterSource().addValue("AnswerId", answerId);
    Optional<Integer> howManyCheckedAnswers =
        Optional.ofNullable(template.queryForObject(sql, sqlParameterSource, Integer.class));
    return howManyCheckedAnswers.orElse(0);
  }

  // TODO (Damian Muca): 6/18/20 move to UserAnswerValidatorDao.
  private void insertToUserAnswerValidator(int userId, int pollId, int validationHashCode) {
    final String insertToUserAnswerValidatorSql =
        "INSERT INTO useranswervalidator(user_id, poll_id, validation_hash_code) "
            + "VALUES (:UserId, :PollId, :ValidationHashCode)";
    SqlParameterSource parameterSource =
        new MapSqlParameterSource()
            .addValue("UserId", userId)
            .addValue("PollId", pollId)
            .addValue("ValidationHashCode", validationHashCode);
    template.update(insertToUserAnswerValidatorSql, parameterSource);
  }
}
