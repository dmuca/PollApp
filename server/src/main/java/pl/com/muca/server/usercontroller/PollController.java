package pl.com.muca.server.usercontroller;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.muca.server.entity.Poll;
import pl.com.muca.server.service.PollService;

@RestController
//@CrossOrigin(origins = "http://localhost:4200")
@CrossOrigin
@RequestMapping("/pollApp")
public class PollController {

  @Resource
  PollService pollService;

  @GetMapping(value = "/listPolls")
  public List<Poll> getPolls(@RequestHeader("Authorization") String token) {
    System.out.println("TOKEN");
    System.out.println(token);
    return pollService.findAll();
  }

  @PostMapping(value = "/createPoll")
  public void createPoll(@RequestBody Poll poll) {
    pollService.insertPoll(poll);
  }

  @PutMapping(value = "/updatePoll")
  public void updatePoll(@RequestBody Poll poll) {
    pollService.updatePoll(poll);
  }

  @PutMapping(value = "/executeUpdatePoll")
  public void executeUpdatePoll(@RequestBody Poll poll) {
    pollService.executeUpdatePoll(poll);
  }

  @DeleteMapping(value = "/deletePoll")
  public void deletePoll(@RequestBody Poll poll) {
    pollService.deletePoll(poll);
  }
}
