package pl.com.muca.server.entity;

import java.util.Arrays;

public class Question {

  private String title;
  private Answer [] answers;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Answer[] getAnswers() {
    return answers;
  }

  public void setAnswers(Answer[] answers) {
    this.answers = answers;
  }

  @Override
  public String toString() {
    return String.format("Question{title='%s', answers=%s}", title,
        Arrays.toString(answers));
  }
}
