package pl.com.muca.server.dao;

import java.util.Arrays;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import pl.com.muca.server.entity.UserAnswer;

@Repository
public class UserAnswerDaoImpl implements UserAnswerDao{
  private final UserDao userDao;
  private final PollDao pollDao;
  private final NamedParameterJdbcTemplate template;

  public UserAnswerDaoImpl(
      NamedParameterJdbcTemplate template) {
    this.template = template;
    this.userDao = new UserDaoImpl(template);
    this.pollDao = new PollDaoImpl(template);
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

  private void insertToUserAnswer(String userIdHash, UserAnswer userAnswer) {
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

  private int generateUserAnswersHashCode(UserAnswer[] userAnswers) {
    return Math.abs(
        Arrays.stream(userAnswers)
            .map(Object::hashCode)
            .mapToInt(Integer::intValue)
            .reduce(Integer::sum)
            .orElse(0));
  }
}
