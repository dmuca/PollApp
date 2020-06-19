package pl.com.muca.server.dao.useranswersvalidator;

import java.util.Arrays;
import pl.com.muca.server.entity.User;
import pl.com.muca.server.entity.UserAnswer;

public class UserAnswersHashCodeGenerator {

  public static int generate(UserAnswer[] userAnswers, User user){
    System.out.println();
    System.out.println();
    System.out.println(Arrays.toString(userAnswers));
    System.out.println("USER ANSWERS HASH CODE: " + Arrays.hashCode(userAnswers));
    int userAnswersHashCode = Arrays.hashCode(userAnswers);
    return Math.abs(user.hashCode() + userAnswersHashCode);
  }
}
