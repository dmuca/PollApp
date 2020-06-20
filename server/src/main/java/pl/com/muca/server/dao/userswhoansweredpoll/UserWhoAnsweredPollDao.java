package pl.com.muca.server.dao.userswhoansweredpoll;

import pl.com.muca.server.entity.UserAnswer;

public interface UserWhoAnsweredPollDao {

  int insertUserWhoAnsweredPoll(UserAnswer[] userAnswers, String token)
      throws Exception;
}
