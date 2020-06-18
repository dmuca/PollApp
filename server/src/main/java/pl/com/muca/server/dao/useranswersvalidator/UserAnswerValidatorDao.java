package pl.com.muca.server.dao.useranswersvalidator;

public interface UserAnswerValidatorDao {

  void insertToUserAnswerValidator(int userId, int pollId,
      int validationHashCode);
}
