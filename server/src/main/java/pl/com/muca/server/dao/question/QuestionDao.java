package pl.com.muca.server.dao.question;

import java.sql.SQLException;
import pl.com.muca.server.entity.Poll;
import pl.com.muca.server.entity.Question;

public interface QuestionDao {

  boolean isUserACreatorOfTheQuestion(String token, int questionId)
      throws SQLException;

  Integer getLatestQuestionId();

  void insertQuestionTableData(Poll poll);

  Question[] getQuestions(int pollId);
}
