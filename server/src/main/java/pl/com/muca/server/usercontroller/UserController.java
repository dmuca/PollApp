package pl.com.muca.server.usercontroller;

import java.util.List;
import javax.annotation.Resource;
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
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/pollApp")
public class UserController {

  @Resource
  UserService userService;

  @GetMapping(value = "/usersList")
  public List<User> getUsers() {
    return userService.findAll();
  }

  @PostMapping(value = "/createUser")
  public void createUser(@RequestBody User user) {
    logAction(user);
    userService.insertUser(user);
  }

  @PutMapping(value = "/updateUser")
  public void updateUser(@RequestBody User user) {
    logAction(user);
    userService.updateUser(user);
  }

  @PutMapping(value = "/executeUpdateUser")
  public void executeUpdateUser(@RequestBody User user) {
    logAction(user);
    userService.executeUpdateUser(user);
  }

  @DeleteMapping(value = "/deleteUser")
  public void deleteUser(@RequestBody User user) {
    logAction(user);
    userService.deleteUser(user);
  }

  // TODO (Damian Muca): 5/18/20 Use log4j to log all action invoked on REST
  //  API.
  private void logAction(@RequestBody User user) {
    String invokingMethodName =
        Thread.currentThread().getStackTrace()[2].getMethodName();
    System.out.printf("%s %s\n", invokingMethodName, user);
  }
}
