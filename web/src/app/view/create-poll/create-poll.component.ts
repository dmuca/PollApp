import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute} from '@angular/router';
import {first} from 'rxjs/operators';
import {AlertService} from '../../model/alert/alert.service';
import {Question} from '../../model/poll/question';
import {Poll} from '../../model/poll/poll';
import {PollService} from '../../model/poll/poll.service';

@Component({
  selector: 'app-create-poll',
  templateUrl: './create-poll.component.html',
  styleUrls: ['./create-poll.component.scss']
})
export class CreatePollComponent implements OnInit {
  pollForm: FormGroup;
  loading = false;
  submitted = false;
  returnUrl: string;

  poll: Poll = {
    pollId: undefined,
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

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private alertService: AlertService,
    private pollService: PollService
  ) {
  }

  ngOnInit(): void {
    this.pollForm = this.formBuilder.group({
      pollName: ['', Validators.required],
    });
    this.returnUrl = this.route.snapshot.queryParams.returnUrl || '/';
  }


  createPoll() {
    this.submitted = true;
    this.alertService.clear();
    this.loading = true;

    console.log(this.poll.name);
    console.log(this.poll.questions);

    if (this.poll.name.length === 0) {
      this.alertService.error('Ankieta musi mieć nazwę.');
      this.loading = false;
      return;
    }

    if (this.poll.questions.length === 0) {
      this.alertService.error('Ankieta musi zawierać pytania.');
      this.loading = false;
      return;
    }

    for (const question of this.poll.questions) {
      if (question.answers.length < 2) {
        this.alertService.error('Każde z pytań musi posiadać przynajmniej dwie odpowiedzi.');
        this.loading = false;
        return;
      } else if (question.title.length === 0) {
        this.alertService.error('Treść pytań nie może być pusta.');
        this.loading = false;
        return;
      }
      for (const answer of question.answers) {
        if (answer.content.length === 0) {
          this.alertService.error('Treść odpowiedzi nie może być pusta.');
          this.loading = false;
          return;
        }
      }
    }

    this.pollService.createPoll(this.poll).pipe(first()).subscribe((response) => {
        this.alertService.success('Ankieta została utworzona.');
      },
      error => {
        this.alertService.error('Błąd serwera, tworzenie ankiety nie powiodło się.');
      });
  }

  get getFormControls() {
    return this.pollForm.controls;
  }

  addQuestion() {
    const newQuestion: Question = {
      questionId: 0,
      title: '',
      answers: [
        {content: '', questionId: 0, answerId: 0},
        {content: '', questionId: 0, answerId: 0},
      ],
    };
    this.poll.questions = this.poll.questions.concat(newQuestion);
  }

  onQuestionChanged(question: Question) {
    console.log(question);
  }
}
