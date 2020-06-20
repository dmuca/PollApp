import {Component, OnInit} from '@angular/core';
import {Poll} from '../../model/poll/poll';
import {ActivatedRoute} from '@angular/router';
import {PollService} from '../../model/poll/poll.service';
import {map} from 'rxjs/operators';
import {User} from '../../model/user/user';

@Component({
  selector: 'app-view-answers-to-my-poll',
  templateUrl: './view-answers-to-my-poll.component.html',
  styleUrls: ['./view-answers-to-my-poll.component.scss']
})
export class ViewAnswersToMyPollComponent implements OnInit {
  public poll: Poll = new Poll();
  public loading: boolean;
  public usersWhoAnsweredPoll: User[] = [];
  public usersWhoDidNotAnswerPoll: User[] = [];

  constructor(private activatedRoute: ActivatedRoute,
              private pollService: PollService) {
  }

  ngOnInit(): void {
    this.activatedRoute.paramMap
    .pipe(map(() => window.history.state))
    .subscribe(state => {
      this.pollService.pollDetails$.subscribe((poll: Poll) => {
        this.poll = poll;
      });
      this.pollService.getPollDetails(state.poll.pollId);

      this.pollService.usersWhoAnsweredPoll$.subscribe((users) => {
        console.log('users');
        console.log(users);
        this.usersWhoAnsweredPoll = users;
      });
      this.pollService.getUsersWhoAnsweredToPoll(state.poll.pollId);

      this.pollService.usersWhoDidNotAnswerPoll$.subscribe((users) => {
        this.usersWhoDidNotAnswerPoll = users;
      });
      this.pollService.getUsersWhoDidNotAnswerToPoll(state.poll.pollId);
    });
  }
}
