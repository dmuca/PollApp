package pl.com.muca.server.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import pl.com.muca.server.entity.User;

public class UserRowMapper implements RowMapper<User> {
  @Override
  public User mapRow(ResultSet resultSet, int i) throws SQLException {
    User user = new User();
    user.setId(resultSet.getInt("user_id"));
    user.setFirstName(resultSet.getString("name"));
    user.setLastName(resultSet.getString("last_name"));
    user.setPassword(resultSet.getString("password"));
    user.setEmail(resultSet.getString("email"));
    return user;
  }
}
