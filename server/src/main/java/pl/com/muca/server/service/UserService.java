package pl.com.muca.server.service;

import javax.security.auth.login.LoginException;
import pl.com.muca.server.entity.User;

public interface UserService {
  void insertUser(User user);

  User login(User user) throws LoginException;
}
