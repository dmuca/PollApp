package pl.com.muca.server.dao;

import java.sql.SQLException;
import java.util.List;
import pl.com.muca.server.entity.Poll;

public interface PollDao {
  List<Poll> findAll(String token);

  List<Poll> findAllMine(String token);

  Poll getPollDetails(int pollId);

  void insertPoll(Poll poll, String token) throws SQLException;

  void updatePoll(Poll poll);

  void executeUpdatePoll(Poll poll);

  void deletePoll(Poll poll);
}
