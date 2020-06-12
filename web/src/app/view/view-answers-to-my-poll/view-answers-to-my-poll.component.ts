import {Component, OnInit} from '@angular/core';
import {Poll} from '../../model/poll/poll';
import {ActivatedRoute} from '@angular/router';
import {PollService} from '../../model/poll/poll.service';
import {map} from 'rxjs/operators';

@Component({
  selector: 'app-view-answers-to-my-poll',
  templateUrl: './view-answers-to-my-poll.component.html',
  styleUrls: ['./view-answers-to-my-poll.component.scss']
})
export class ViewAnswersToMyPollComponent implements OnInit {
  public poll: Poll = new Poll();
  loading: boolean;

  constructor(private activatedRoute: ActivatedRoute,
              private pollService: PollService) {
  }

  ngOnInit(): void {
    this.activatedRoute.paramMap
    .pipe(map(() => window.history.state))
    .subscribe(state => {
      this.pollService.getPollDetails(state.poll.pollId).subscribe((poll: Poll) => {
        this.poll = poll;
      });
    });
  }
}
