package pl.com.muca.server.entity;

public class UserAnswer {
  private int userIdHash;
  private int questionId;
  private int answerChosen;

  public int getUserIdHash() {
    return userIdHash;
  }

  public void setUserIdHash(int userIdHash) {
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
    return String.format("UserAnswer{quesionId=%d, answerChosen=%d}", questionId, answerChosen);
  }
}
