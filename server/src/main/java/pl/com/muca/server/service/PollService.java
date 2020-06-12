package pl.com.muca.server.service;

import java.sql.SQLException;
import java.util.List;
import pl.com.muca.server.entity.Poll;

public interface PollService {

  List<Poll> findAllMine(String token);

  List<Poll> findAll(String token);

  void insertPoll(Poll poll, String token) throws SQLException;

  void updatePoll(Poll poll);

  void executeUpdatePoll(Poll poll);

  void deletePoll(Poll poll);
}
