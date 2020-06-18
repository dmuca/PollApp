package pl.com.muca.server.dao.user;

import com.google.common.collect.ImmutableList;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.hibernate.SessionException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import pl.com.muca.server.encryption.Cryptographer;
import pl.com.muca.server.entity.User;
import pl.com.muca.server.mapper.UserRowMapper;

@Repository
public class UserDaoImpl implements UserDao {

  private static final String UPDATE_SQL =
      "UPDATE appuser SET name=:name, last_name=:last_name, "
          + "password=:password, email=:email "
          + "WHERE user_id=:user_id";

  private final NamedParameterJdbcTemplate template;

  public UserDaoImpl(NamedParameterJdbcTemplate template) {
    this.template = template;
  }

  @Override
  public ImmutableList<User> findAll() {
    return ImmutableList.copyOf(template.query("SELECT * FROM appuser", new UserRowMapper()));
  }

  @Override
  public void insertUser(User user) {
    final String sql =
        "INSERT INTO appuser("
            + "user_id, name, last_name, password, email) "
            + "VALUES (:user_id, :name, :last_name, :password, :email)";

    KeyHolder holder = new GeneratedKeyHolder();
    SqlParameterSource param =
        new MapSqlParameterSource()
            // TODO (Damian Muca): 5/28/20 handle hash generating.
            .addValue("user_id", user.getEmail().trim().hashCode())
            .addValue("name", user.getFirstName().trim())
            .addValue("last_name", user.getLastName().trim())
            .addValue("password", user.getPassword())
            .addValue("email", user.getEmail().trim());
    template.update(sql, param, holder);
  }

  @Override
  public void updateUser(User user) {
    KeyHolder holder = new GeneratedKeyHolder();
    SqlParameterSource param =
        new MapSqlParameterSource()
            .addValue("user_id", user.getId())
            .addValue("name", user.getFirstName().trim())
            .addValue("last_name", user.getLastName().trim())
            .addValue("password", user.getPassword())
            .addValue("email", user.getEmail().trim());
    template.update(UPDATE_SQL, param, holder);
  }

  @Override
  public void executeUpdateUser(User user) {
    Map<String, Object> map = new HashMap<>();
    map.put("user_id", user.getId());
    map.put("name", user.getFirstName().trim());
    map.put("last_name", user.getLastName().trim());
    map.put("password", user.getPassword());
    map.put("email", user.getEmail().trim());

    template.execute(
        UPDATE_SQL, map, (PreparedStatementCallback<Object>) PreparedStatement::executeUpdate);
  }

  @Override
  public void deleteUser(User user) {
    final String sql = "DELETE FROM appuser WHERE user_id=:user_id";
    final Map<String, Object> map = new HashMap<>();
    map.put("user_id", user.getId());
    template.execute(
        sql, map, (PreparedStatementCallback<Object>) PreparedStatement::executeUpdate);
  }

  @Override
  public void createSession(User user) {
    final String sql = "INSERT INTO session(user_id) VALUES (:user_id)";

    KeyHolder holder = new GeneratedKeyHolder();
    SqlParameterSource namedParameters =
        new MapSqlParameterSource().addValue("user_id", user.getId());
    template.update(sql, namedParameters, holder);
  }

  @Override
  public String getLastSessionToken(int userId) {
    final String sql =
        "SELECT access_token FROM session "
            + "INNER JOIN  ( "
            + "             SELECT user_id AS userId, MAX(granted) as grantDate "
            + "             FROM session "
            + "             WHERE user_id = :UserId "
            + "             GROUP BY user_id "
            + "            ) as newestdate "
            + "ON session.granted = newestdate.grantDate "
            + "AND session.user_id = newestdate.userId;";
    SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("UserId", userId);

    Optional<String> sessionToken =
        Optional.ofNullable(template.queryForObject(sql, namedParameters, String.class));

    if (sessionToken.isEmpty()) {
      throw new SessionException(
          String.format("Could not find session for specified user with id: %d", userId));
    }
    ;
    return sessionToken.orElse("");
  }

  @Override
  public String getUserHashIdFromToken(String token) throws Exception {
    int userId = getUserId(token);
    return Cryptographer.encrypt(String.format("dzien dobry %d", userId), userId);
  }

  @Override
  public User getUser(int userId) {
    String getUserSql = "SELECT * FROM appuser WHERE user_id = :UserId";
    SqlParameterSource parameterSource = new MapSqlParameterSource().addValue("UserId", userId);
    return template.queryForObject(getUserSql, parameterSource, new UserRowMapper());
  }

  @Override
  public int getUserId(String token) throws SQLException {
    final String requestorUserIdSql =
        "SELECT appuser.user_id FROM appuser "
            + "INNER JOIN session on appuser.user_id = session.user_id "
            + "WHERE session.access_token = :SessionToken;";
    SqlParameterSource sessionTokenParam =
        new MapSqlParameterSource().addValue("SessionToken", UUID.fromString(token));
    Optional<Integer> userIdOptional =
        Optional.ofNullable(
            template.queryForObject(requestorUserIdSql, sessionTokenParam, Integer.class));

    if (userIdOptional.isEmpty()) {
      throw new SQLException("Couldn't find user id hash base on its session token");
    }
    return userIdOptional.get();
  }
}
