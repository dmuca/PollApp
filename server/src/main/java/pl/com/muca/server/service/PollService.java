package pl.com.muca.server.service;

import java.util.List;
import pl.com.muca.server.entity.Poll;

public interface PollService {

  List<Poll> findAll(String token);

  void insertPoll(Poll poll);

  void updatePoll(Poll poll);

  void executeUpdatePoll(Poll poll);

  void deletePoll(Poll poll);
}
