package pl.com.muca.server.dao.question;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import pl.com.muca.server.entity.Question;

/**
 * Mapper responsible for mapping row data from table 'question' table to {@link pl.com.muca.server.entity.Question} object.
 */
public class QuestionRowMapper implements RowMapper<Question> {
  @Override
  public Question mapRow(ResultSet resultSet, int i) throws SQLException {
    Question question = new Question();
    question.setQuestionId(resultSet.getInt("question_id"));
    question.setPollId(resultSet.getInt("poll_id"));
    question.setTitle(resultSet.getString("content"));
    return question;
  }
}
