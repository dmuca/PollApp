package pl.com.muca.server.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import pl.com.muca.server.entity.Poll;
import pl.com.muca.server.entity.PollState;

public class PollRowMapper implements RowMapper<Poll> {

  @Override
  public Poll mapRow(ResultSet resultSet, int i) throws SQLException {
    Poll poll = new Poll();
    poll.setPollId(resultSet.getInt("poll_id"));
    poll.setOwnerUserId(resultSet.getInt("owner_user_id"));
    poll.setName(resultSet.getString("name"));
    poll.setState(PollState.New);
    return poll;
  }
}
