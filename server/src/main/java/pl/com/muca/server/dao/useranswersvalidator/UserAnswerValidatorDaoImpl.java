package pl.com.muca.server.dao.useranswersvalidator;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import pl.com.muca.server.dao.poll.PollDaoImpl;
import pl.com.muca.server.dao.question.QuestionDao;
import pl.com.muca.server.dao.user.UserDao;
import pl.com.muca.server.dao.useranswer.UserAnswerDao;
import pl.com.muca.server.dao.useranswer.UserAnswerDaoImpl;
import pl.com.muca.server.entity.User;
import pl.com.muca.server.entity.UserAnswer;
import pl.com.muca.server.entity.UserAnswersValidator;

@Repository
public class UserAnswerValidatorDaoImpl implements UserAnswerValidatorDao {
  private final UserDao userDao;
  private final UserAnswerDao userAnswerDao;
  private final PollDaoImpl pollDao;
  private final NamedParameterJdbcTemplate template;

  // TODO (Damian Muca): 6/19/20 Use builder pattern.
  public UserAnswerValidatorDaoImpl(
      NamedParameterJdbcTemplate template,
      PollDaoImpl pollDao,
      UserDao userDao,
      UserAnswerDaoImpl userAnswerDao) {
    this.userDao = userDao;
    this.userAnswerDao = userAnswerDao;
    this.pollDao = pollDao;
    this.template = template;
  }

  @Override
  public int insertToUserAnswerValidator(UserAnswer[] userAnswers, String token)
      throws Exception {
    int pollId = this.pollDao.getPollId(userAnswers[0].getQuestionId());
    int userId = this.userDao.getUserId(token);
    for (UserAnswer userAnswer : userAnswers) {
      String userHashId = this.userDao.getUserHashIdFromToken(token);
      userAnswer.setUserIdHash(userHashId);
    }
    int validationHashCode =
        UserAnswersHashCodeGenerator.generate(userAnswers, this.userDao.getUser(userId));

    insertToUserAnswersValidatorTable(pollId, userId, validationHashCode);
    logInfo(pollId, userId, validationHashCode);
    return validationHashCode;
  }

  private void insertToUserAnswersValidatorTable(int pollId, int userId, int validationHashCode) {
    final String insertToUserAnswerValidatorSql =
        "INSERT INTO useranswersvalidator(user_id, poll_id, validation_hash_code) "
            + "VALUES (:UserId, :PollId, :ValidationHashCode)";
    SqlParameterSource parameterSource =
        new MapSqlParameterSource()
            .addValue("UserId", userId)
            .addValue("PollId", pollId)
            .addValue("ValidationHashCode", validationHashCode);
    template.update(insertToUserAnswerValidatorSql, parameterSource);
  }

  private void logInfo(int pollId, int userId, int validationHashCode) {
    String time = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now());
    System.out.printf(
        "%s User with id: %d, answered on poll id: %d, generated validation hash code: %d\n",
        time, userId, pollId, validationHashCode);
  }

  @Override
  public boolean validateAnswers(UserAnswersValidator userAnswersValidator, String userToken)
      throws Exception {
    UserAnswer[] userAnswers =
        this.userAnswerDao.getUserAnswersForPoll(userToken, userAnswersValidator.getPollId());
    User user = userDao.getUser(userDao.getUserId(userToken));
    int generate = UserAnswersHashCodeGenerator.generate(userAnswers, user);


    System.out.println("GENERATED HASH CODE: " + generate);
    System.out.println("PASSED HASH CODE: " + userAnswersValidator.getValidationHashCode());
    return generate
        == userAnswersValidator.getValidationHashCode();
  }
}
