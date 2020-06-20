package pl.com.muca.server.usercontroller;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Resource;
import javax.validation.ValidationException;
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
import pl.com.muca.server.entity.UserAnswersValidator;
import pl.com.muca.server.service.PollService;

@RestController
@CrossOrigin
@RequestMapping("/pollApp")
public class PollController {
  @Resource PollService pollService;
  @Resource UserDao userDao;

  @GetMapping(value = "/listPolls")
  public List<Poll> getPolls(@RequestHeader("Authorization") String token) throws Exception {
    logAction();
    return pollService.findAll(token);
  }

  @GetMapping(value = "/listMyPolls")
  public List<Poll> getMyPolls(@RequestHeader("Authorization") String token) {
    logAction();
    return pollService.findAllMine(token);
  }

  @GetMapping(value = "/getPollDetails/{pollId}")
  public Poll getPollDetails(
      @PathVariable Integer pollId, @RequestHeader("Authorization") String token) throws Exception {
    return pollService.getPollDetails(pollId, token);
  }

  @PostMapping(value = "/createPoll")
  public void createPoll(@RequestHeader("Authorization") String token, @RequestBody Poll poll)
      throws SQLException {
    logAction(poll.toString());
    pollService.insertPoll(poll, token);
  }

  @PostMapping(value = "/saveUserAnswers")
  public int createPoll(
      @RequestHeader("Authorization") String userAuthorizationToken,
      @RequestBody UserAnswer[] answers)
      throws Exception {
    logAction(Arrays.toString(answers));
    return pollService.saveUserAnswers(answers, userAuthorizationToken);
  }

  @PostMapping(value = "/verifyPollAnswers")
  public boolean verifyPollAnswers(
      @RequestHeader("Authorization") String userAuthorizationToken,
      @RequestBody UserAnswersValidator userAnswersValidator)
      throws Exception {
    userAnswersValidator.setUserId(this.userDao.getUserId(userAuthorizationToken));
    logAction(userAnswersValidator.toString());
    return pollService.verifyPollAnswers(userAnswersValidator, userAuthorizationToken);
  }

  @PostMapping(value = ("/getUsersWhoAnsweredToPoll"))
  public User[] getUsersAnsweredPoll(@RequestBody int pollId){
    return pollService.getUsersAnsweredPoll(pollId);
  }

  @PostMapping(value = ("/getUsersWhoDidNotAnswerToPoll"))
  public User[] getUsersDidNotAnswerPoll(@RequestBody int pollId){
    return pollService.getUsersDidNotAnswerPoll(pollId);
  }

  @PutMapping(value = "/updatePoll")
  public void updatePoll(@RequestBody Poll poll) {
    logAction(poll.toString());
    pollService.updatePoll(poll);
  }

  @PutMapping(value = "/executeUpdatePoll")
  public void executeUpdatePoll(@RequestBody Poll poll) {
    logAction(poll.toString());
    pollService.executeUpdatePoll(poll);
  }

  @DeleteMapping(value = "/deletePoll")
  public void deletePoll(@RequestBody Poll poll) {
    logAction(poll.toString());
    pollService.deletePoll(poll);
  }

  // TODO (Damian Muca): 5/18/20 log4j to log all action invoked on REST API.
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
