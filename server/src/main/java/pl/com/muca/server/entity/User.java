package pl.com.muca.server.entity;

public class User {

  private int userIdHash;
  private String name;
  private String lastName;
  private String passwordHash;
  private String email;

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

  public String getPasswordHash() {
    return passwordHash;
  }

  public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public String toString() {
    return String.format(
        "{"
            + "userIdHash=%d, "
            + "name='%s', "
            + "lastName='%s', "
            + "passwordHash='%s', "
            + "email='%s'"
            + "}",
        userIdHash, name, lastName, passwordHash, email);
  }
}
