package pl.com.muca.server.useranswershashcodegenerator;

import java.util.Arrays;
import pl.com.muca.server.entity.User;
import pl.com.muca.server.entity.UserAnswer;

/**
 * Generates validation hash codes for user poll responses.
 * @author Damian Muca
 */
public class UserAnswersHashCodeGenerator {
  /** Generates a validation hash code using {@link Object#hashCode()} and {@link Arrays#hashCode(Object[])}} methods.*/
  public static int generateHashCode(UserAnswer[] userAnswers, User user){
    return Math.abs(user.hashCode() + Arrays.hashCode(userAnswers));
  }
}
