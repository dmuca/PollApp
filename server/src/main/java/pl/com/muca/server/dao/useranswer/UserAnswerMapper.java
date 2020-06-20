package pl.com.muca.server.dao.useranswer;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import pl.com.muca.server.entity.UserAnswer;

/**
 * Mapper responsible for mapping row data from table 'useranswer' table to {@link pl.com.muca.server.entity.UserAnswer} object.
 */
public class UserAnswerMapper implements RowMapper<UserAnswer> {

  @Override
  public UserAnswer mapRow(ResultSet resultSet, int i) throws SQLException {
    UserAnswer userAnswer = new UserAnswer();
    userAnswer.setUserIdHash(resultSet.getString("user_id_hash"));
    userAnswer.setQuestionId(resultSet.getInt("question_id"));
    userAnswer.setAnswerChosen(resultSet.getInt("answer_chosen"));
    return userAnswer;
  }
}
