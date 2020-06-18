package pl.com.muca.server.dao.user;

import com.google.common.collect.ImmutableList;
import java.sql.SQLException;
import pl.com.muca.server.entity.User;

public interface UserDao {

  ImmutableList<User> findAll();

  void insertUser(User user);

  void createSession(User user);

  String getLastSessionToken(int userId);

  void updateUser(User user);

  void executeUpdateUser(User user);

  void deleteUser(User user);

  int getUserId(String token) throws SQLException;

  String getUserHashIdFromToken(String token) throws Exception;
}
