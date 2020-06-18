package pl.com.muca.server.dao;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class UserAnswerValidatorDaoImpl implements UserAnswerValidatorDao {
  private NamedParameterJdbcTemplate template;

  public UserAnswerValidatorDaoImpl(NamedParameterJdbcTemplate template) {
    this.template = template;
  }

  @Override
  public void insertToUserAnswerValidator(int userId, int pollId, int validationHashCode) {
    final String insertToUserAnswerValidatorSql =
        "INSERT INTO useranswervalidator(user_id, poll_id, validation_hash_code) "
            + "VALUES (:UserId, :PollId, :ValidationHashCode)";
    SqlParameterSource parameterSource =
        new MapSqlParameterSource()
            .addValue("UserId", userId)
            .addValue("PollId", pollId)
            .addValue("ValidationHashCode", validationHashCode);
    template.update(insertToUserAnswerValidatorSql, parameterSource);
  }
}
