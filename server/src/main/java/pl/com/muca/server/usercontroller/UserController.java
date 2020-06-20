package pl.com.muca.server.usercontroller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.annotation.Resource;
import javax.security.auth.login.LoginException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.muca.server.entity.User;
import pl.com.muca.server.service.UserService;

/** REST API controller for User related operations like Login or Register. */
@RestController
@CrossOrigin
@RequestMapping("/pollApp")
public class UserController {

  @Resource UserService userService;

  /**
   * Registers user by inserting its data to the 'appuser' database table.
   *
   * @param user {@link User} object with user data
   */
  @PostMapping(value = "/registerUser")
  public void registerUser(@RequestBody User user) {
    logAction(user.toString());
    userService.registerUser(user);
  }

  /**
   * Login user by verifying password and generating a session token in 'session' database table.
   *
   * @param user {@link User} object with user email and password
   * @return {@link User} object with generated {@link User#token} session token
   * @throws LoginException when credentials are incorrect
   */
  @PostMapping(value = "/login")
  public User login(@RequestBody User user) throws LoginException {
    logAction(user.toString());
    return userService.login(user);
  }

  private void logAction() {
    String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
    logAction("", methodName);
  }

  private void logAction(String info) {
    String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
    logAction(info, methodName);
  }

  private void logAction(String info, String methodName) {
    String time = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now());
    System.out.printf("%s API Method (%s), Data %s\n", time, methodName, info);
  }
}
