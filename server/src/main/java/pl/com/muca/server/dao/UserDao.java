package pl.com.muca.server.dao;

import com.google.common.collect.ImmutableList;
import pl.com.muca.server.entity.User;

public interface UserDao {

  ImmutableList<User> findAll();

  void insertUser(User user);

  void createSession(User user);

  String getLastSessionToken(int userId);

  void updateUser(User user);

  void executeUpdateUser(User user);

  void deleteUser(User user);
}
