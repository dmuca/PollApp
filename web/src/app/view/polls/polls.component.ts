import {Component, OnInit} from '@angular/core';
import {Poll} from '../../model/poll';
import {PollService} from '../../model/poll.service';

@Component({
  selector: 'app-polls',
  templateUrl: './polls.component.html',
  styleUrls: ['./polls.component.scss']
})
export class PollsComponent implements OnInit {
  polls: Poll[];

  constructor(private pollService: PollService) {
    pollService.pollsList$.subscribe((polls) => {
      this.polls = polls;
    });
  }

  ngOnInit(): void {
    this.pollService.listPolls();
  }

}
