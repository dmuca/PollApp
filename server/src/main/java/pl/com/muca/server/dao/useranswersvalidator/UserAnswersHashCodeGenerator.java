package pl.com.muca.server.dao.useranswersvalidator;

import java.util.Arrays;
import pl.com.muca.server.entity.User;
import pl.com.muca.server.entity.UserAnswer;

public class UserAnswersHashCodeGenerator {

  public static int generate(UserAnswer[] userAnswers, User user){
    return Math.abs(user.hashCode() + Arrays.hashCode(userAnswers));
  }
}
