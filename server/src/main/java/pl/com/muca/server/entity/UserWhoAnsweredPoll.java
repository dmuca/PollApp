package pl.com.muca.server.entity;

public class UserWhoAnsweredPoll {
  private int userId;
  private int pollId;
  private int validationHashCode;

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public int getPollId() {
    return pollId;
  }

  public void setPollId(int pollId) {
    this.pollId = pollId;
  }

  public int getValidationHashCode() {
    return validationHashCode;
  }

  public void setValidationHashCode(int validationHashCode) {
    this.validationHashCode = validationHashCode;
  }

  @Override
  public String toString() {
    return "UserWhoAnsweredPoll{" +
        "userId=" + userId +
        ", pollId=" + pollId +
        ", validationHashCode=" + validationHashCode +
        '}';
  }
}
