package pl.com.muca.server.dao.question;

import java.sql.SQLException;
import java.util.Optional;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import pl.com.muca.server.dao.user.UserDao;
import pl.com.muca.server.dao.user.UserDaoImpl;
import pl.com.muca.server.entity.Poll;
import pl.com.muca.server.entity.Question;

/**
 * Implementation of {@link pl.com.muca.server.dao.question.QuestionDao}.
 */
@Repository
public class QuestionDaoImpl implements QuestionDao {
  private NamedParameterJdbcTemplate template;
  private UserDao userDao;

  public QuestionDaoImpl(NamedParameterJdbcTemplate template) {
    this.template = template;
    this.userDao = new UserDaoImpl(template);
  }

  @Override
  public boolean isUserACreatorOfTheQuestion(String token, int questionId) throws SQLException {
    String sql =
        "SELECT COUNT(*) FROM question "
            + "INNER JOIN poll "
            + "ON poll.poll_id = question.poll_id "
            + "WHERE question.question_id = :QuestionId AND poll.owner_user_id = :UserId;";
    SqlParameterSource sqlParameterSource =
        new MapSqlParameterSource()
            .addValue("QuestionId", questionId)
            .addValue("UserId", this.userDao.getUserId(token));
    return Optional.ofNullable(template.queryForObject(sql, sqlParameterSource, Integer.class))
            .orElse(0)
        > 0;
  }


  @Override
  public Integer getLatestQuestionId() {
    final String latestQuestionIdSql = "SELECT MAX(question.question_id) " + "FROM question;";
    return Optional.ofNullable(
        template.queryForObject(
            latestQuestionIdSql, new MapSqlParameterSource(), Integer.class))
        .orElse(0);
  }

  @Override
  public void insertQuestionTableData(Poll poll) {
    final String sql =
        "INSERT INTO question(question_id, poll_id, content) "
            + "VALUES (:question_id, :poll_id, :content)";

    for (Question question : poll.getQuestions()) {
      SqlParameterSource param =
          new MapSqlParameterSource()
              .addValue("question_id", question.getQuestionId())
              .addValue("poll_id", question.getPollId())
              .addValue("content", question.getTitle());
      template.update(sql, param);
    }
  }

  @Override
  public Question[] getQuestions(int pollId) {
    String sql =
        "SELECT question.question_id, question.poll_id, question.content "
            + "FROM question "
            + "WHERE question.poll_id = :PollId;";
    SqlParameterSource questionParameters = new MapSqlParameterSource().addValue("PollId", pollId);
    return template
        .query(sql, questionParameters, new QuestionRowMapper())
        .toArray(Question[]::new);
  }
}
