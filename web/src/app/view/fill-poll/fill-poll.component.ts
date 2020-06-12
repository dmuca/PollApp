import {Component, OnInit} from '@angular/core';
import {Poll} from '../../model/poll/poll';
import {ActivatedRoute} from '@angular/router';
import {UserAnswer} from '../../model/poll/user.answer';
import {map} from 'rxjs/operators';
import {PollService} from '../../model/poll/poll.service';

@Component({
  selector: 'app-fill-poll',
  templateUrl: './fill-poll.component.html',
  styleUrls: ['./fill-poll.component.scss']
})
export class FillPollComponent implements OnInit {
  public poll: Poll = new Poll();
  userAnswers: UserAnswer[] = [];
  loading: boolean;

  constructor(private activatedRoute: ActivatedRoute, private pollService: PollService) {
  }

  ngOnInit(): void {
    this.activatedRoute.paramMap
    .pipe(map(() => window.history.state))
    .subscribe(state => {
      this.pollService.getPollDetails(state.poll.pollId).subscribe((poll) => {
        this.poll = poll;
        this.userAnswers = new Array(this.poll.questions.length);
        for (let i = 0; i < this.poll.questions.length; ++i) {
          this.userAnswers[i] = {
            questionId: this.poll.questions[i].questionId,
            answerChosen: -1,
          };
        }
      });
    });
  }

  answerOnPoll() {
    console.log(this.poll.pollId);
  }
}
