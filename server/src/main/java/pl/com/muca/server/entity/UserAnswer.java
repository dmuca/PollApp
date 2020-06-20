package pl.com.muca.server.entity;

import java.util.Objects;

/**
 * POJO representation of 'useranswer' table.
 */
public class UserAnswer {
  private String userIdHash;
  private int questionId;
  private int answerChosen;

  public String getUserIdHash() {
    return userIdHash;
  }

  public void setUserIdHash(String userIdHash) {
    this.userIdHash = userIdHash;
  }

  public int getQuestionId() {
    return questionId;
  }

  public void setQuestionId(int questionId) {
    this.questionId = questionId;
  }

  public int getAnswerChosen() {
    return answerChosen;
  }

  public void setAnswerChosen(int answerChosen) {
    this.answerChosen = answerChosen;
  }

  @Override
  public String toString() {
    return String
        .format("UserAnswer{userIdHash='%s', questionId=%d, answerChosen=%d}",
            userIdHash, questionId, answerChosen);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof UserAnswer)) {
      return false;
    }
    UserAnswer that = (UserAnswer) o;
    return getUserIdHash() == that.getUserIdHash() &&
        getQuestionId() == that.getQuestionId() &&
        getAnswerChosen() == that.getAnswerChosen();
  }

  @Override
  public int hashCode() {
    return Objects.hash(getUserIdHash(), getQuestionId(), getAnswerChosen());
  }
}
