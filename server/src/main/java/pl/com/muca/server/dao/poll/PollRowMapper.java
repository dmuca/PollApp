package pl.com.muca.server.dao.poll;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import pl.com.muca.server.entity.Poll;

/**
 * Mapper responsible for mapping row data from table 'poll' table to {@link pl.com.muca.server.entity.Poll} object.
 */
public class PollRowMapper implements RowMapper<Poll> {

  @Override
  public Poll mapRow(ResultSet resultSet, int i) throws SQLException {
    Poll poll = new Poll();
    poll.setPollId(resultSet.getInt("poll_id"));
    poll.setOwnerUserId(resultSet.getInt("owner_user_id"));
    poll.setName(resultSet.getString("name"));
    return poll;
  }
}
