package pl.com.muca.server.entity;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {

  //  @GeneratedValue(strategy = GenerationType.AUTO)
  @Id
  private int userIdHash;
  private String name;
  private String lastName;
  private String email;

  public User() {}

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (object == null || getClass() != object.getClass()) {
      return false;
    }
    User user = (User) object;
    return userIdHash == user.userIdHash &&
        Objects.equals(name, user.name) &&
        Objects.equals(email, user.email);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userIdHash, name, email);
  }

  public long getUserIdHash() {
    return userIdHash;
  }

  public void setUserIdHash(int userIdHash) {
    this.userIdHash = userIdHash;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
