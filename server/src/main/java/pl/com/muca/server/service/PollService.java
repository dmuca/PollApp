package pl.com.muca.server.service;

import java.sql.SQLException;
import java.util.List;
import pl.com.muca.server.entity.Poll;
import pl.com.muca.server.entity.User;
import pl.com.muca.server.entity.UserAnswer;
import pl.com.muca.server.entity.UserWhoAnsweredPoll;

public interface PollService {

  List<Poll> findAllMine(String token);

  List<Poll> findAll(String token) throws Exception;

  void insertPoll(Poll poll, String token) throws SQLException;

  Poll getPollDetails(int pollId, String token) throws Exception;

  int saveUserAnswers(UserAnswer[] answers, String userAuthorizationToken)
      throws Exception;

  int generateUserAnswersValidationHashCode(
      UserWhoAnsweredPoll userWhoAnsweredPoll,
      User user, String userAuthorizationToken)
      throws Exception;

  List<User> getUsersAnsweredPoll(int pollId);
}
