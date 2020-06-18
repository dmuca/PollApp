package pl.com.muca.server.service;

import java.sql.SQLException;
import java.util.List;
import pl.com.muca.server.entity.Poll;
import pl.com.muca.server.entity.UserAnswer;

public interface PollService {

  List<Poll> findAllMine(String token);

  List<Poll> findAll(String token) throws Exception;

  void insertPoll(Poll poll, String token) throws SQLException;

  void updatePoll(Poll poll);

  void executeUpdatePoll(Poll poll);

  void deletePoll(Poll poll);

  Poll getPollDetails(int pollId, String token) throws Exception;

  int saveUserAnswers(UserAnswer[] answers, String token) throws Exception;
}
