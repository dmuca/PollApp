package pl.com.muca.server.dao.user;

import com.google.common.collect.ImmutableList;
import java.sql.SQLException;
import pl.com.muca.server.entity.Poll;
import pl.com.muca.server.entity.Question;
import pl.com.muca.server.entity.User;

public interface UserDao {

  ImmutableList<User> findAll();

  void insertUser(User user);

  void createSession(User user);

  String getLatestSessionTokenForUser(int userId);

  int getLatestUserId();

  void updateUser(User user);

  void executeUpdateUser(User user);

  void deleteUser(User user);

  int getUserId(String token) throws SQLException;

  String getUserHashIdFromToken(String token) throws Exception;

  User getUser(int userId);
}
