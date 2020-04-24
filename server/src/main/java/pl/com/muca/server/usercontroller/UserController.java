package pl.com.muca.server.usercontroller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
  private UserRepository userRepository;

  public UserController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @GetMapping("/users")
//  @RequestMapping(method = RequestMethod.GET, name = "/users")
  public Iterable<User> getUsers() {
    return userRepository.findAll();
  }

  @PostMapping(path = "/users")
  void addUser(@RequestBody User user) {
    userRepository.save(user);
  }
}
