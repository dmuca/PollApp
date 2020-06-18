package pl.com.muca.server.dao.poll;

import java.sql.SQLException;
import java.util.List;
import pl.com.muca.server.entity.Poll;
import pl.com.muca.server.entity.PollState;

public interface PollDao {
  List<Poll> findAll(String token) throws Exception;

  PollState getPollState(int pollId, String token) throws Exception;

  List<Poll> findAllMine(String token);

  Poll getPollDetails(int pollId, String token) throws Exception;

  void insertPoll(Poll poll, String token) throws SQLException;

  Integer getLatestPollId();

  void insertPollTableData(Poll poll);

  void updatePoll(Poll poll);

  void executeUpdatePoll(Poll poll);

  void deletePoll(Poll poll);

  String getPollName(int pollId);

  int getPollId(int questionId) throws SQLException;
}
