package pl.com.muca.server.dao.question;

import java.sql.SQLException;

public interface QuestionDao {

  boolean isUserACreatorOfTheQuestion(String token, int questionId)
      throws SQLException;
}
