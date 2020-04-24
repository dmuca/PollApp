package pl.com.muca.server.usercontroller;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Getter
  private long id;

  @Getter
  private String name;

  @Getter
  private String email;

  public User() {
  }

  private User(String name, String email) {
    this.name = name;
    this.email = email;
  }

  public static User from(String name) {
    return User.from(name, name.toLowerCase() + "@gmail.com");
  }

  public static User from(String name, String email) {
    return new User(name, email);
  }
}
