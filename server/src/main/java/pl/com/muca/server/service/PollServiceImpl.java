package pl.com.muca.server.service;

import java.sql.SQLException;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import pl.com.muca.server.dao.PollDao;
import pl.com.muca.server.dao.UserAnswerDao;
import pl.com.muca.server.entity.Poll;
import pl.com.muca.server.entity.UserAnswer;

@Component
public class PollServiceImpl implements PollService {
  @Resource PollDao pollDao;
  @Resource UserAnswerDao userAnswerDao;

  @Override
  public List<Poll> findAll(String token) throws Exception {
    return pollDao.findAll(token);
  }

  @Override
  public List<Poll> findAllMine(String token) {
    return pollDao.findAllMine(token);
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
}
