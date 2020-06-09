package pl.com.muca.server.entity;

public class Poll {

  private int pollId;
  private int ownerUserId;
  private String name;
  private PollState state;

  public int getPollId() {
    return pollId;
  }

  public void setPollId(int pollId) {
    this.pollId = pollId;
  }

  public int getOwnerUserId() {
    return ownerUserId;
  }

  public void setOwnerUserId(int ownerUserId) {
    this.ownerUserId = ownerUserId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setState(PollState state) {
    this.state = state;
  }

  public PollState getState() {
    return state;
  }

  @Override
  public String toString() {
    return String.format("Poll{pollId=%d, ownerUserId=%d, name='%s', status='%s'}", pollId,
        ownerUserId, name, state);
  }
}
