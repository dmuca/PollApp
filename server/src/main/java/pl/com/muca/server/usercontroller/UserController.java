package pl.com.muca.server.usercontroller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.annotation.Resource;
import javax.security.auth.login.LoginException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.muca.server.entity.User;
import pl.com.muca.server.service.UserService;

@RestController
@CrossOrigin
@RequestMapping("/pollApp")
public class UserController {

  @Resource UserService userService;

  @PostMapping(value = "/registerUser")
  public void registerUser(@RequestBody User user) {
    logAction(user.toString());
    userService.insertUser(user);
  }

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
