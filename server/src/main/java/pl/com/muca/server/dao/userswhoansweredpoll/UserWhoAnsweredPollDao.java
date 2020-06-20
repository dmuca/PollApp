package pl.com.muca.server.dao.userswhoansweredpoll;

import java.util.List;
import pl.com.muca.server.entity.User;
import pl.com.muca.server.entity.UserAnswer;

/**
 * API methods for modifying and reading from the 'userwhoansweredpoll' table.
 */
public interface UserWhoAnsweredPollDao {

  int insertUserWhoAnsweredPoll(UserAnswer[] userAnswers, String token)
      throws Exception;

  List<User> getUsersWhoAnsweredPoll(int pollId);
}
