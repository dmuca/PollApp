import { Component, OnInit } from '@angular/core';
import {Poll} from '../../model/poll/poll';
import {PollService} from '../../model/poll/poll.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-my-polls',
  templateUrl: './my-polls.component.html',
  styleUrls: ['./my-polls.component.scss']
})
export class MyPollsComponent implements OnInit {
  polls: Poll[];

  constructor(private pollService: PollService,
              private router: Router) {
    pollService.myPollsList$.subscribe((polls) => {
      this.polls = polls;
    });
  }

  ngOnInit(): void {
    this.pollService.listMyPolls();
  }

  viewAnswersToMyPoll(poll: Poll) {
    this.router.navigateByUrl('/viewAnswersToMyPoll', {state: {poll}});
  }
}
