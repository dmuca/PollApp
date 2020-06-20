package pl.com.muca.server.pollanswersvalidator;

import java.util.Arrays;
import pl.com.muca.server.entity.User;
import pl.com.muca.server.entity.UserAnswer;

public class UserAnswersHashCodeValidator {
  public static int generateHashCode(UserAnswer[] userAnswers, User user){
    return Math.abs(user.hashCode() + Arrays.hashCode(userAnswers));
  }
}
