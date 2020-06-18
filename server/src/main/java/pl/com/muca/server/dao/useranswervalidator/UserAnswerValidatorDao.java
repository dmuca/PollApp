package pl.com.muca.server.dao.useranswervalidator;

public interface UserAnswerValidatorDao {

  void insertToUserAnswerValidator(int userId, int pollId,
      int validationHashCode);
}
