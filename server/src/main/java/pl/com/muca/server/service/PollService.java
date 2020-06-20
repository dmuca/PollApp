package pl.com.muca.server.service;

import java.sql.SQLException;
import java.util.List;
import pl.com.muca.server.entity.Poll;
import pl.com.muca.server.entity.User;
import pl.com.muca.server.entity.UserAnswer;
import pl.com.muca.server.entity.UserAnswersValidator;

public interface PollService {

  List<Poll> findAllMine(String token);

  List<Poll> findAll(String token) throws Exception;

  void insertPoll(Poll poll, String token) throws SQLException;

  void updatePoll(Poll poll);

  void executeUpdatePoll(Poll poll);

  void deletePoll(Poll poll);

  Poll getPollDetails(int pollId, String token) throws Exception;

  int saveUserAnswers(UserAnswer[] answers, String userAuthorizationToken)
      throws Exception;

  boolean verifyPollAnswers(UserAnswersValidator answers, String userAuthorizationToken)
      throws Exception;

  User[] getUsersAnsweredPoll(int pollId);

  User[] getUsersDidNotAnswerPoll(int pollId);
}
