package pl.com.muca.server.entity;

import java.util.Arrays;

/**
 * POJO representation of 'question' table.
 */
public class Question {

  private String title;
  private Answer [] answers;
  private int pollId;
  private int questionId;

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

  public int getPollId() {
    return pollId;
  }

  public void setPollId(int pollId) {
    this.pollId = pollId;
  }

  public int getQuestionId() {
    return questionId;
  }

  public void setQuestionId(int questionId) {
    this.questionId = questionId;
  }

  @Override
  public String toString() {
    return String.format("Question{title='%s', answers=%s}", title,
        Arrays.toString(answers));
  }
}
