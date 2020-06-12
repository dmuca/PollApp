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
  // public poll: Poll = new Poll();
  poll: Poll = {
    pollId: -1,
    name: 'Przykładowa ankieta',
    questions: [
      {
        questionId: 1,
        title: 'Ile masz lat?',
        answers: [{
          content: '10',
          answerId: 1,
          questionId: 1
        }
          , {
            content: '20',
            answerId: 2,
            questionId: 1
          }, {
            content: '70',
            answerId: 3,
            questionId: 1
          }],
      },
      {
        questionId: 2,
        title: 'Gdzie mieszkasz?',
        answers: [{
          content: 'Wieś',
          answerId: 4,
          questionId: 2
        }, {
          content: 'Miast do 50 tyś mieszkańców',
          answerId: 5,
          questionId: 2
        }, {
          content: 'Miasto powyżej 50 tys mieszkańców',
          answerId: 6,
          questionId: 2
        }],
      }
    ],
    state: undefined,
  };

  userAnswers: UserAnswer[] = [{
    questionId: -1,
    answerChosen: -1,
  }, {
    questionId: -1,
    answerChosen: -1,
  }];

  loading: boolean;

  constructor(private activatedRoute: ActivatedRoute, private pollService: PollService) {
  }

  ngOnInit(): void {
    this.activatedRoute.paramMap
    .pipe(map(() => window.history.state))
    // TODO invoke service, because here poll contains only pollId, state and name,
    // TODO need to provide questions
    .subscribe(state => {
      console.log(`Get pollId: ${state.poll.pollId}`);
      this.pollService.getPollDetails(state.poll.pollId).subscribe((poll) => {
        // this.poll = poll;
        this.userAnswers = new Array(this.poll.questions.length);
        for (let i = 0; i < this.poll.questions.length; ++i) {
          this.userAnswers[i] = {
            questionId: this.poll.questions[i].questionId,
            answerChosen: -1,
          };
        }
      });
    });

    this.userAnswers = new Array(this.poll.questions.length);
    for (let i = 0; i < this.poll.questions.length; ++i) {
      this.userAnswers[i] = {
        questionId: this.poll.questions[i].questionId,
        answerChosen: -1,
      };
    }
  }

  answerOnPoll() {
    console.log(...this.userAnswers);
    console.log(this.poll.pollId);
  }
}
