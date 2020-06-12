package pl.com.muca.server.entity;

public class Answer {
  private int answerId;
  private int questionId;
  private String content;
  private boolean markedByUser;
  private int answersCounter;

  public int getAnswerId() {
    return answerId;
  }

  public void setAnswerId(int answerId) {
    this.answerId = answerId;
  }

  public int getQuestionId() {
    return questionId;
  }

  public void setQuestionId(int questionId) {
    this.questionId = questionId;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public boolean isMarkedByUser() {
    return markedByUser;
  }

  public void setMarkedByUser(boolean markedByUser) {
    this.markedByUser = markedByUser;
  }

  public int getAnswersCounter() {
    return answersCounter;
  }

  public void setAnswersCounter(int answersCounter) {
    this.answersCounter = answersCounter;
  }

  @Override
  public String toString() {
    return String.format("Answer{content='%s'}", content);
  }
}
