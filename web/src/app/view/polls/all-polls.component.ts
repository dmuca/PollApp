import {Component, OnInit} from '@angular/core';
import {Poll} from '../../model/poll/poll';
import {PollService} from '../../model/poll/poll.service';
import {PollState} from '../../model/poll/poll.state';
import {ActivatedRoute, Router} from '@angular/router';
import {FillPollComponent} from '../fill-poll/fill-poll.component';
import {AuthGuard} from '../../model/authentication/auth.guard';

@Component({
  selector: 'app-polls',
  templateUrl: './all-polls.component.html',
  styleUrls: ['./all-polls.component.scss']
})
export class AllPollsComponent implements OnInit {
  polls: Poll[];

  constructor(
    private pollService: PollService,
    private router: Router) {
    pollService.pollsList$.subscribe((polls) => {
      this.polls = polls;
    });
  }

  ngOnInit(): void {
    this.pollService.listAllPolls();
  }

  isPollNew(poll: Poll): boolean {
    return poll.state === PollState.New;
  }

  fillPoll(poll: Poll) {
    this.router.navigateByUrl('/fillPoll', {state: {poll}});
  }

  viewMyAnswers(poll: Poll) {
    console.log(`Fill poll: ${poll}`);
  }
}
