package pl.com.muca.server.dao;

import pl.com.muca.server.entity.UserAnswer;

public interface UserAnswerDao {
  int saveUserAnswers(UserAnswer[] answers, String token) throws Exception;
}
