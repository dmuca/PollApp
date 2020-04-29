package pl.com.muca.server.usercontroller;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

public class UserTest {

  @Test
  public void fromName_shouldGenerateEmailProperly() {
    String name = "Damian";
    String email = "damian@muca.com.pl";
    User expectedResult = User.from(name, email);

    User actualResult = User.from(name);

    assertEquals(actualResult, expectedResult);
  }
}
