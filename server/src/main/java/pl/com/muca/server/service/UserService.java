package pl.com.muca.server.service;

import java.util.List;
import pl.com.muca.server.entity.User;

public interface UserService {

  List<User> findAll();

  void insertUser(User user);

  void updateUser(User user);

  void executeUpdateUser(User user);

  void deleteUser(User user);

  boolean login(User user);
}
