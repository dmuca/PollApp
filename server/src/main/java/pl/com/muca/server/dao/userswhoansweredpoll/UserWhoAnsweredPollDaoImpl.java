package pl.com.muca.server.dao.userswhoansweredpoll;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import pl.com.muca.server.dao.poll.PollDaoImpl;
import pl.com.muca.server.dao.user.UserDao;
import pl.com.muca.server.dao.user.UserRowMapper;
import pl.com.muca.server.entity.User;
import pl.com.muca.server.entity.UserAnswer;
import pl.com.muca.server.pollanswersvalidator.UserAnswersHashCodeValidator;

/**
 * Implementation of {@link pl.com.muca.server.dao.userswhoansweredpoll.UserWhoAnsweredPollDao}.
 */
@Repository
public class UserWhoAnsweredPollDaoImpl implements UserWhoAnsweredPollDao {
  private final UserDao userDao;
  private final PollDaoImpl pollDao;
  private final NamedParameterJdbcTemplate template;

  // TODO (Damian Muca): 6/19/20 Use builder pattern.
  public UserWhoAnsweredPollDaoImpl(
      NamedParameterJdbcTemplate template,
      PollDaoImpl pollDao,
      UserDao userDao) {
    this.template = template;
    this.pollDao = pollDao;
    this.userDao = userDao;
  }

  @Override
  public int insertUserWhoAnsweredPoll(UserAnswer[] userAnswers, String token)
      throws Exception {
    int pollId = this.pollDao.getPollId(userAnswers[0].getQuestionId());
    int userId = this.userDao.getUserId(token);
    for (UserAnswer userAnswer : userAnswers) {
      String userHashId = this.userDao.getUserHashIdFromToken(token);
      userAnswer.setUserIdHash(userHashId);
    }
    int validationHashCode =
        UserAnswersHashCodeValidator.generateHashCode(userAnswers, this.userDao.getUser(userId));

    insertToUserWhoAnsweredPoll(pollId, userId);
    logInfo(pollId, userId, validationHashCode);
    return validationHashCode;
  }

  private void insertToUserWhoAnsweredPoll(int pollId, int userId) {
    final String insertToUserWhoAnsweredPoll =
        "INSERT INTO userswhoansweredpoll(user_id, poll_id) "
            + "VALUES (:UserId, :PollId)";
    SqlParameterSource parameterSource =
        new MapSqlParameterSource()
            .addValue("UserId", userId)
            .addValue("PollId", pollId);
    template.update(insertToUserWhoAnsweredPoll, parameterSource);
  }

  private void logInfo(int pollId, int userId, int validationHashCode) {
    String time = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now());
    System.out.printf(
        "%s User with id: %d, answered on poll id: %d, generated validation hash code: %d\n",
        time, userId, pollId, validationHashCode);
  }

  @Override
  public List<User> getUsersWhoAnsweredPoll(int pollId) {
    String sql = "SELECT appuser.user_id, appuser.name, appuser.last_name, 'uknown' as password, appuser.email "
        + "FROM appuser "
        + "INNER JOIN userswhoansweredpoll "
        + "ON userswhoansweredpoll.user_id = appuser.user_id "
        + "WHERE userswhoansweredpoll.poll_id = :PollId";
    SqlParameterSource parameterSource = new MapSqlParameterSource().addValue("PollId", pollId);
    return template.query(sql, parameterSource, new UserRowMapper());
  }
}
