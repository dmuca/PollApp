package pl.com.muca.server.dao;

import java.util.List;
import pl.com.muca.server.entity.User;

public interface UserDao {

  List<User> findAll();

  void insertUser(User user);

  void updateUser(User user);

  void executeUpdateUser(User user);

  void deleteUser(User user);
}
