package pl.com.muca.server.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import pl.com.muca.server.dao.UserDao;
import pl.com.muca.server.entity.User;

@Component
public class UserServiceImpl implements UserService {

  @Resource
  UserDao userDao;

  @Override
  public List<User> findAll() {
    return userDao.findAll();
  }

  @Override
  public void insertUser(User user) {
    userDao.insertUser(user);
  }

  @Override
  public void updateUser(User user) {
    userDao.updateUser(user);
  }

  @Override
  public void executeUpdateUser(User user) {
    userDao.executeUpdateUser(user);
  }

  @Override
  public void deleteUser(User user) {
    userDao.deleteUser(user);
  }

  @Override
  public User login(User userCredentials) {
    // TODO (Damian Muca): 5/30/20 add find method.
    Optional<User> optionalUser = userDao.findAll().stream()
        .filter(u -> u.getEmail().equals(userCredentials.getEmail().trim()))
        .filter(u -> u.getPassword().equals(userCredentials.getPassword()))
        .findAny();

    if (optionalUser.isEmpty()){
      return null;
    }
    else{
      User user = optionalUser.get();
      String token = createSession(user);
      user.setToken(token);
      return user;
    }
  }

  private String createSession(User user) {
    return "FAKE TOKEN";
  }
}
