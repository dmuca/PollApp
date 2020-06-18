package pl.com.muca.server.dao.poll;

import java.sql.SQLException;
import java.util.List;
import pl.com.muca.server.entity.Poll;

public interface PollDao {
  List<Poll> findAll(String token) throws Exception;

  List<Poll> findAllMine(String token);

  Poll getPollDetails(int pollId, String token) throws Exception;

  void insertPoll(Poll poll, String token) throws SQLException;

  void updatePoll(Poll poll);

  void executeUpdatePoll(Poll poll);

  void deletePoll(Poll poll);

  int getPollId(int questionId) throws SQLException;
}
