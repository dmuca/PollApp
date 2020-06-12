package pl.com.muca.server.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import pl.com.muca.server.entity.Answer;

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
