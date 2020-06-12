import {Component, OnInit} from '@angular/core';
import {Poll} from '../../model/poll/poll';
import {ActivatedRoute} from '@angular/router';
import {map} from 'rxjs/operators';
import {PollState} from '../../model/poll/poll.state';

@Component({
  selector: 'app-fill-poll',
  templateUrl: './fill-poll.component.html',
  styleUrls: ['./fill-poll.component.scss']
})
export class FillPollComponent implements OnInit {
  public poll: Poll = new Poll();

  constructor(private activatedRoute: ActivatedRoute) {
    this.poll = {
      pollId: 0,
      name: '',
      questions: [],
      state: PollState.New
    };
  }

  ngOnInit(): void {
    this.activatedRoute.paramMap
    .pipe(map(() => window.history.state))
    .subscribe(state => this.poll = state.poll);
  }
}
