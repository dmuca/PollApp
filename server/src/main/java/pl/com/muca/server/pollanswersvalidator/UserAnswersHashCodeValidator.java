package pl.com.muca.server.pollanswersvalidator;

import java.util.Arrays;
import pl.com.muca.server.entity.User;
import pl.com.muca.server.entity.UserAnswer;
import pl.com.muca.server.entity.UserWhoAnsweredPoll;

public class UserAnswersHashCodeValidator {

  public boolean validate(UserWhoAnsweredPoll userWhoAnsweredPoll,
      int userAnswersValidationHashCode) {
    // TODO (Damian Muca): 6/20/20 compare to the database data.
    return userAnswersValidationHashCode == 0;
  }

  public static int generateHashCode(UserAnswer[] userAnswers, User user){
    return Math.abs(user.hashCode() + Arrays.hashCode(userAnswers));
  }
}
