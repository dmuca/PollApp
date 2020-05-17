package pl.com.muca.server;

import java.util.stream.Stream;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.com.muca.server.entity.User;
import pl.com.muca.server.usercontroller.UserRepository;

@SpringBootApplication
public class ServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(ServerApplication.class, args);
  }

  @Bean
  CommandLineRunner init(UserRepository userRepository) {
    User damian = new User();
    damian.setName("Katarzyna");
    damian.setLastName("Maleńczuk");
    damian.setEmail("KM@gmail.com");
    damian.setUserIdHash(123);
    return args -> Stream.of(damian).forEach(userRepository::save);
  }
}
