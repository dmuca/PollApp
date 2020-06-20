package pl.com.muca.server.service;

import java.sql.SQLException;
import java.util.List;
import javax.annotation.Resource;
import javax.validation.ValidationException;
import org.springframework.stereotype.Component;
import pl.com.muca.server.dao.poll.PollDao;
import pl.com.muca.server.dao.user.UserDao;
import pl.com.muca.server.dao.useranswer.UserAnswerDao;
import pl.com.muca.server.dao.useranswersvalidator.UserAnswerValidatorDao;
import pl.com.muca.server.entity.Poll;
import pl.com.muca.server.entity.User;
import pl.com.muca.server.entity.UserAnswer;
import pl.com.muca.server.entity.UserAnswersValidator;

@Component
public class PollServiceImpl implements PollService {
  @Resource PollDao pollDao;
  @Resource UserAnswerDao userAnswerDao;
  @Resource UserAnswerValidatorDao userAnswerValidatorDao;

  @Override
  public List<Poll> findAll(String token) throws Exception {
    return pollDao.findAllPolls(token);
  }

  @Override
  public List<Poll> findAllMine(String token) {
    return pollDao.findAllMyPolls(token);
  }

  @Override
  public void insertPoll(Poll poll, String token) throws SQLException {
    pollDao.insertPoll(poll, token);
  }

  @Override
  public int saveUserAnswers(UserAnswer[] answers, String token) throws Exception {
    return userAnswerDao.saveUserAnswers(answers, token);
  }

  @Override
  public void updatePoll(Poll poll) {
    pollDao.updatePoll(poll);
  }

  @Override
  public void executeUpdatePoll(Poll poll) {
    pollDao.executeUpdatePoll(poll);
  }

  @Override
  public void deletePoll(Poll poll) {
    pollDao.deletePoll(poll);
  }

  @Override
  public Poll getPollDetails(int pollId, String token) throws Exception {
    return pollDao.getPollDetails(pollId, token);
  }

  @Override
  public boolean verifyPollAnswers(
      UserAnswersValidator userAnswersValidator, String userAuthorizationToken)
      throws Exception {
    return userAnswerValidatorDao.validateAnswers(userAnswersValidator, userAuthorizationToken);
  }

  @Override
  public User[] getUsersAnsweredPoll(int pollId) {
    System.out.println("POLL ID: " + pollId);
    User user = new User();
    user.setFirstName("Kasia");
    user.setLastName("Barbara");
    user.setEmail("KB@wp.pl");
    return new User[]{user};
  }

  @Override
  public User[] getUsersDidNotAnswerPoll(int pollId) {
    System.out.println("POLL ID: " + pollId);
    User user = new User();
    user.setFirstName("Bartosz ");
    user.setLastName("Niedobry");
    user.setEmail("Zły@wp.pl");
    return new User[]{user};
  }
}
