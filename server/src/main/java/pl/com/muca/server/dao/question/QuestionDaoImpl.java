package pl.com.muca.server.dao.question;

import java.sql.SQLException;
import java.util.Optional;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import pl.com.muca.server.dao.user.UserDao;
import pl.com.muca.server.dao.user.UserDaoImpl;

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
}
