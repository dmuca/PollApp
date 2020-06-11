import { Component, OnInit } from '@angular/core';
import {Poll} from '../../model/poll/poll';
import {PollService} from '../../model/poll/poll.service';

@Component({
  selector: 'app-my-polls',
  templateUrl: './my-polls.component.html',
  styleUrls: ['./my-polls.component.scss']
})
export class MyPollsComponent implements OnInit {
  polls: Poll[];

  constructor(private pollService: PollService) {
    pollService.myPollsList$.subscribe((polls) => {
      this.polls = polls;
    });
  }

  ngOnInit(): void {
    this.pollService.listMyPolls();
  }

  viewAnswers(poll: Poll) {
    console.log(`Fill poll: ${poll}`);
  }
}
