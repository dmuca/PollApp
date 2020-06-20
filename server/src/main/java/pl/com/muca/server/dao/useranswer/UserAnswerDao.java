package pl.com.muca.server.dao.useranswer;

import pl.com.muca.server.entity.Answer;
import pl.com.muca.server.entity.Poll;
import pl.com.muca.server.entity.UserAnswer;

/**
 * API methods for modifying and reading from the 'useranswer' table.
 */
public interface UserAnswerDao {
  int saveUserAnswers(UserAnswer[] answers, String token) throws Exception;

  void saveUserAnswer(UserAnswer userAnswer, String token)
      throws Exception;

  Answer[] getAnswersCounterForPollOwner(int questionId, String token) throws Exception;

  boolean isAnswerMarkedByUser(int answerId, String token) throws Exception;

  int countAnswers(int answerId);

  Integer getLatestAnswerId();

  void insertAnswerTableData(Poll poll);

  UserAnswer[] getUserAnswersForPoll(String userToken, int pollId)
      throws Exception;
}
