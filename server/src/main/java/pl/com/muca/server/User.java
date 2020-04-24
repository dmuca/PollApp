package pl.com.muca.server;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
  private String name; // TODO (Damian Muca): 4/24/20 TRY MAKE FINAL ?
  private String email; // TODO (Damian Muca): 4/24/20 TRY MAKE FINAL ?

  // standard constructors / setters / getters / toString

  public User() {}

  public User(String name, String email) {
    this.name = name;
    this.email = email;
  }

  @Override
  public String toString() {
    return String.format("User{id=%d, name='%s', email='%s'}", id, name, email);
  }
}

