package pl.com.muca.server.dao.useranswersvalidator;

import pl.com.muca.server.entity.UserAnswer;
import pl.com.muca.server.entity.UserAnswersValidator;

public interface UserAnswerValidatorDao {

  int insertToUserAnswerValidator(UserAnswer[] userAnswers, String token)
      throws Exception;

  boolean validateAnswers(UserAnswersValidator userAnswersValidator,
      String userToken)
      throws Exception;
}
