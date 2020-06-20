package pl.com.muca.server.service;

import java.sql.SQLException;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import pl.com.muca.server.dao.poll.PollDao;
import pl.com.muca.server.dao.useranswer.UserAnswerDao;
import pl.com.muca.server.dao.userswhoansweredpoll.UserWhoAnsweredPollDao;
import pl.com.muca.server.entity.Poll;
import pl.com.muca.server.entity.User;
import pl.com.muca.server.entity.UserAnswer;
import pl.com.muca.server.entity.UserWhoAnsweredPoll;
import pl.com.muca.server.pollanswersvalidator.UserAnswersHashCodeValidator;

@Component
public class PollServiceImpl implements PollService {
  @Resource PollDao pollDao;
  @Resource UserAnswerDao userAnswerDao;
  @Resource UserWhoAnsweredPollDao userWhoAnsweredPollDao;

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
  public int generateUserAnswersValidationHashCode(
      UserWhoAnsweredPoll userWhoAnsweredPoll, User user, String userToken) throws Exception {
    UserAnswer[] userAnswers =
        this.userAnswerDao.getUserAnswersForPoll(userToken, userWhoAnsweredPoll.getPollId());
    return UserAnswersHashCodeValidator.generateHashCode(userAnswers, user);
  }

  @Override
  public List<User> getUsersAnsweredPoll(int pollId) {
    return this.userWhoAnsweredPollDao.getUsersWhoAnsweredPoll(pollId);
  }
}
