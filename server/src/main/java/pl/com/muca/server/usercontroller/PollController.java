package pl.com.muca.server.usercontroller;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.muca.server.dao.user.UserDao;
import pl.com.muca.server.entity.Poll;
import pl.com.muca.server.entity.User;
import pl.com.muca.server.entity.UserAnswer;
import pl.com.muca.server.entity.UserWhoAnsweredPoll;
import pl.com.muca.server.service.PollService;

/**
 * REST API controller for Polls' related operations.
 */
@RestController
@CrossOrigin
@RequestMapping("/pollApp")
public class PollController {
  @Resource PollService pollService;
  @Resource UserDao userDao;

  /**
   * Lists all polls available in the system.
   * @param token User session token
   * @return {@link List<Poll>}
   * @throws Exception if could not read polls from database
   */
  @GetMapping(value = "/listPolls")
  public List<Poll> getPolls(@RequestHeader("Authorization") String token) throws Exception {
    logAction();
    return pollService.findAll(token);
  }

  /**
   * Lists all polls created by the user.
   * User is determined base on the session token.
   * @param token User session token
   * @return {@link List<Poll>}
   */
  @GetMapping(value = "/listMyPolls")
  public List<Poll> getMyPolls(@RequestHeader("Authorization") String token) {
    logAction();
    return pollService.findAllMine(token);
  }

  /**
   * Provides detailed information about the poll.
   * @param pollId Id of requested poll
   * @param token User session token
   * @return detailed poll info
   * @throws Exception if could not read poll details from database
   */
  @GetMapping(value = "/getPollDetails/{pollId}")
  public Poll getPollDetails(
      @PathVariable Integer pollId, @RequestHeader("Authorization") String token) throws Exception {
    return pollService.getPollDetails(pollId, token);
  }

  /**
   * Accepts {@link Poll} object and inserts its data to the database.
   * @param token User session token
   * @param poll poll to create
   * @throws SQLException if could not insert poll data to the database
   */
  @PostMapping(value = "/createPoll")
  public void createPoll(@RequestHeader("Authorization") String token, @RequestBody Poll poll)
      throws SQLException {
    logAction(poll.toString());
    pollService.insertPoll(poll, token);
  }

  /**
   * Accepts user answers in the form of Array of {@link UserAnswer} objects and inserts its data to the database.
   * @param token User session token
   * @param answers Array of {@link UserAnswer} objects
   * @return Validation hash code for user answers.
   * @throws Exception if could not insert data to the database
   */
  @PostMapping(value = "/saveUserAnswers")
  public int createPoll(
      @RequestHeader("Authorization") String token,
      @RequestBody UserAnswer[] answers)
      throws Exception {
    logAction(Arrays.toString(answers));
    return pollService.saveUserAnswers(answers, token);
  }

  /**
   * Validates user answers hash code for a given poll.
   * Checks if validation hash code generated base on user data and user answers matches
   * the validation code provided in params.
   * @param token User session token
   * @param userWhoAnsweredPoll Object of {@link UserWhoAnsweredPoll}
   * @return determines whether user answers hash code passed matches answers in the database
   * @throws Exception if have problems with reading data from database
   */
  @PostMapping(value = "/verifyPollAnswers")
  public boolean verifyPollAnswers(
      @RequestHeader("Authorization") String token,
      @RequestBody UserWhoAnsweredPoll userWhoAnsweredPoll)
      throws Exception {
    int userId = this.userDao.getUserId(token);
    userWhoAnsweredPoll.setUserId(userId);
    logAction(userWhoAnsweredPoll.toString());
    int userAnswersValidationHashCode =
        pollService.generateUserAnswersValidationHashCode(
            userWhoAnsweredPoll, this.userDao.getUser(userId), token);
    return userAnswersValidationHashCode == userWhoAnsweredPoll.getValidationHashCode();
  }

  /**
   * Returns all users who already answered to a poll.
   * @param pollId ID of the poll
   * @return List of {@link User} objects
   */
  @PostMapping(value = ("/getUsersWhoAnsweredToPoll"))
  public List<User> getUsersAnsweredPoll(@RequestBody int pollId) {
    return pollService.getUsersAnsweredPoll(pollId);
  }

  /**
   * Returns all users who did not answer yet to a poll.
   * @param pollId ID of the poll
   * @return List of {@link User} objects
   */
  @PostMapping(value = ("/getUsersWhoDidNotAnswerToPoll"))
  public List<User> getUsersDidNotAnswerPoll(@RequestBody int pollId) {
    List<User> usersAnsweredPoll = pollService.getUsersAnsweredPoll(pollId);
    return userDao.findAll().stream()
        .filter(user -> !usersAnsweredPoll.contains(user))
        .collect(Collectors.toList());
  }

  private void logAction() {
    String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
    logAction("", methodName);
  }

  private void logAction(String info) {
    String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
    logAction(info, methodName);
  }

  private void logAction(String info, String methodName) {
    String time = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now());
    System.out.printf("%s API Method (%s), Data %s\n", time, methodName, info);
  }
}
