package pl.com.muca.server.dao;

import java.util.List;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import pl.com.muca.server.entity.User;
import pl.com.muca.server.mapper.UserRowMapper;

@Repository
public class UserDaoImpl implements UserDao {
  NamedParameterJdbcTemplate template;

  public UserDaoImpl(NamedParameterJdbcTemplate template) {
    this.template = template;
  }

  @Override
  public List<User> findAll() {
    return template.query("SELECT * FROM appuser", new UserRowMapper());
  }

  @Override
  public void insertUser(User user) {
    System.out.println("INSERT USER");
  }

  @Override
  public void updateUser(User user) {
    System.out.println("UPDATE USER");
  }

  @Override
  public void executeUpdateUser(User user) {
    System.out.println("EXECUTE UPDATE USER");
  }

  @Override
  public void deleteUser(User user) {
    System.out.println("DELETE USER");
  }
}
