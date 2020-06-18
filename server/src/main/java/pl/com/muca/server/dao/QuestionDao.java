package pl.com.muca.server.dao;

import java.sql.SQLException;

public interface QuestionDao {

  boolean isUserACreatorOfTheQuestion(String token, int questionId)
      throws SQLException;
}
