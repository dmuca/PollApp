package pl.com.muca.server.dao.useranswer;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import pl.com.muca.server.entity.Answer;

/**
 * Mapper responsible for mapping row data from table 'answer' table to {@link pl.com.muca.server.entity.Answer} object.
 */
public class AnswerRowMapper implements RowMapper<Answer> {

  @Override
  public Answer mapRow(ResultSet resultSet, int i) throws SQLException {
    Answer answer = new Answer();
    answer.setAnswerId(resultSet.getInt("answer_id"));
    answer.setQuestionId(resultSet.getInt("question_id"));
    answer.setContent(resultSet.getString("content"));
    return answer;
  }
}
