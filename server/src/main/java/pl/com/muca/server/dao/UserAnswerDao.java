package pl.com.muca.server.dao;

import pl.com.muca.server.entity.Answer;
import pl.com.muca.server.entity.UserAnswer;

public interface UserAnswerDao {
  int saveUserAnswers(UserAnswer[] answers, String token) throws Exception;

  void insertToUserAnswer(String userIdHash, UserAnswer userAnswer);

  Answer[] getAnswers(int questionId, String token) throws Exception;

  boolean isAnswerMarkedByUser(int answerId, String token) throws Exception;

  int countAnswers(int answerId);
}
