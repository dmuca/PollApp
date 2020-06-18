package pl.com.muca.server.dao.useranswer;

import java.util.Arrays;
import java.util.Optional;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import pl.com.muca.server.dao.poll.PollDao;
import pl.com.muca.server.dao.poll.PollDaoImpl;
import pl.com.muca.server.dao.question.QuestionDao;
import pl.com.muca.server.dao.question.QuestionDaoImpl;
import pl.com.muca.server.dao.useranswervalidator.UserAnswerValidatorDaoImpl;
import pl.com.muca.server.dao.user.UserDao;
import pl.com.muca.server.dao.user.UserDaoImpl;
import pl.com.muca.server.entity.Answer;
import pl.com.muca.server.entity.UserAnswer;

@Repository
public class UserAnswerDaoImpl implements UserAnswerDao {
  private final NamedParameterJdbcTemplate template;
  private final UserDao userDao;
  private final PollDao pollDao;
  private final QuestionDao questionDao;
  private final UserAnswerValidatorDaoImpl userAnswerValidatorDao;

  public UserAnswerDaoImpl(NamedParameterJdbcTemplate template, PollDaoImpl pollDao) {
    this.template = template;
    this.userDao = new UserDaoImpl(template);
    this.pollDao = pollDao;
    this.questionDao = new QuestionDaoImpl(template);
    this.userAnswerValidatorDao = new UserAnswerValidatorDaoImpl(template);
  }

  @Override
  public int saveUserAnswers(UserAnswer[] userAnswers, String token) throws Exception {
    String userIdHash = this.userDao.getUserHashIdFromToken(token);
    Arrays.stream(userAnswers).forEach(userAnswer -> insertToUserAnswer(userIdHash, userAnswer));

    int pollId = this.pollDao.getPollId(userAnswers[0].getQuestionId());
    int userId = this.userDao.getUserId(token);

    int userAnswersHashCode = Arrays.hashCode(userAnswers);
    int userHashCode = this.userDao.getUser(userId).hashCode();
    int validationHashCode = Math.abs(userHashCode + userAnswersHashCode);
    this.userAnswerValidatorDao.insertToUserAnswerValidator(userId, pollId, validationHashCode);
    return userAnswersHashCode;
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
}
