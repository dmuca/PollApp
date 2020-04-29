package pl.com.muca.server.usercontroller;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;

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
    return User.from(name, name.toLowerCase() + "@muca.com.pl");
  }

  public static User from(String name, String email) {
    return new User(name, email);
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (object == null || getClass() != object.getClass()) {
      return false;
    }
    User user = (User) object;
    return id == user.id &&
        Objects.equals(name, user.name) &&
        Objects.equals(email, user.email);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, email);
  }
}
