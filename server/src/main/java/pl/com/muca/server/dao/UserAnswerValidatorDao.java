package pl.com.muca.server.dao;

public interface UserAnswerValidatorDao {

  void insertToUserAnswerValidator(int userId, int pollId,
      int validationHashCode);
}
