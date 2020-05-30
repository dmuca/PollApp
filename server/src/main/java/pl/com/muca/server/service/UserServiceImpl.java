package pl.com.muca.server.service;

import java.util.List;
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
  public boolean login(User userCredentials) {
    // TODO (Damian Muca): 5/30/20 add find method.
    return userDao.findAll().stream()
        .anyMatch(
            u -> u.getEmail().equals(userCredentials.getEmail()) && u.getPasswordHash()
                .equals(userCredentials.getPasswordHash()));
  }
}
