package pl.com.muca.server.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import pl.com.muca.server.entity.User;

public class UserRowMapper implements RowMapper<User> {
  @Override
  public User mapRow(ResultSet resultSet, int i) throws SQLException {
    User user = new User();
    user.setIdHash(resultSet.getInt("user_id_hash"));
    user.setFirstName(resultSet.getString("name"));
    user.setLastName(resultSet.getString("last_name"));
    user.setPassword(resultSet.getString("password_hash"));
    user.setEmail(resultSet.getString("email"));
    return user;
  }
}
