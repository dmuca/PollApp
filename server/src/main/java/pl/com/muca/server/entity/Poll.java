package pl.com.muca.server.entity;

import java.util.Arrays;

/**
 * POJO representation of 'poll' table.
 */
public class Poll {

  private int pollId;
  private String name;
  private Question [] questions;
  private PollState state;
  private int ownerUserId;

  public int getPollId() {
    return pollId;
  }

  public void setPollId(int pollId) {
    this.pollId = pollId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Question[] getQuestions() {
    return questions;
  }

  public void setQuestions(Question[] questions) {
    this.questions = questions;
  }

  public PollState getState() {
    return state;
  }

  public void setState(PollState state) {
    this.state = state;
  }

  public int getOwnerUserId() {
    return ownerUserId;
  }

  public void setOwnerUserId(int ownerUserId) {
    this.ownerUserId = ownerUserId;
  }

  @Override
  public String toString() {
    return String.format(
        "Poll{pollId=%d, name='%s', questions=%s, state=%s, ownerUserId=%d}",
        pollId, name, Arrays.toString(questions), state, ownerUserId);
  }
}
