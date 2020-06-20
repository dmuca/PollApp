package pl.com.muca.server.dao.userswhoansweredpoll;

import java.util.List;
import pl.com.muca.server.entity.User;
import pl.com.muca.server.entity.UserAnswer;

public interface UserWhoAnsweredPollDao {

  int insertUserWhoAnsweredPoll(UserAnswer[] userAnswers, String token)
      throws Exception;

  List<User> getUsersWhoAnsweredPoll(int pollId);
}
