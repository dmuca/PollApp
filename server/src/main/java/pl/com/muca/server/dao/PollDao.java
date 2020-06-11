package pl.com.muca.server.dao;

import java.util.List;
import pl.com.muca.server.entity.Poll;

public interface PollDao {
  List<Poll> findAll(String token);

  List<Poll> findAllMine(String token);

  void insertPoll(Poll poll);

  void updatePoll(Poll poll);

  void executeUpdatePoll(Poll poll);

  void deletePoll(Poll poll);
}
