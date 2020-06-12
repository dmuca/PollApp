package pl.com.muca.server.entity;

public class Answer {
  private String content;

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  @Override
  public String toString() {
    return String.format("Answer{content='%s'}", content);
  }
}
