import {Component, OnInit} from '@angular/core';
import {Poll} from '../../model/poll';
import {PollService} from '../../model/poll.service';
import {PollState} from '../../model/poll.state';

@Component({
  selector: 'app-polls',
  templateUrl: './all-polls.component.html',
  styleUrls: ['./all-polls.component.scss']
})
export class AllPollsComponent implements OnInit {
  polls: Poll[];

  constructor(private pollService: PollService) {
    pollService.pollsList$.subscribe((polls) => {
      this.polls = polls;
    });
  }

  ngOnInit(): void {
    this.pollService.listAllPolls();
  }

  fillPoll(poll: Poll) {
    console.log(`Fill poll: ${poll}`);
  }

  isPollNew(poll: Poll): boolean {
    return poll.state === PollState.New;
  }
}
