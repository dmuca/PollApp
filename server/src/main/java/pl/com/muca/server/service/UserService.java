package pl.com.muca.server.service;

import javax.security.auth.login.LoginException;
import pl.com.muca.server.entity.User;

/**
 * API with higher level of abstraction for performing users' related operations on DAO classes.
 * Acts like an adapter (@see <a href="https://en.wikipedia.org/wiki/Adapter_pattern">Adapter
 * Pattern</a>) of DAO classes for the purposes of {@link
 * pl.com.muca.server.usercontroller.UserController}.
 */
public interface UserService {
  void registerUser(User user);

  User login(User user) throws LoginException;
}
