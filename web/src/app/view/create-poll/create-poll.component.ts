import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute} from '@angular/router';
import {User} from '../../model/user/user';
import {first} from 'rxjs/operators';
import {AlertService} from '../../model/alert/alert.service';
import {Question} from '../../model/poll/question';
import {Poll} from '../../model/poll/poll';

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
    name: '',
    questions: [
      {
        title: 'Ile masz lat?',
        answers: [{content: '10'}, {content: '20'}, {content: '70'}],
      },
      {
        title: 'Gdzie mieszkasz?',
        answers: [{content: 'Wieś'}, {content: 'Miast do 50 tyś mieszkańców'}, {content: 'Miasto powyżej 50 tys mieszkańców'}],
      }
    ],
    state: undefined,
  };

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private alertService: AlertService) {
  }

  ngOnInit(): void {
    this.pollForm = this.formBuilder.group({
      pollName: ['', Validators.required],
    });
    this.returnUrl = this.route.snapshot.queryParams.returnUrl || '/';
  }

  onSubmit() {
    // this.submitted = true;
    // this.alertService.clear();
    // if (this.pollForm.invalid) {
    //   return;
    // }
    // this.loading = true;

    // this.alertService.error('sample error');
    console.log(this.poll.name)
    console.log(this.poll.questions);
  }

  get getFormControls() {
    return this.pollForm.controls;
  }

  addQuestion() {
    const newQuestion: Question = {
      title: '',
      answers: [],
    };
    this.poll.questions = this.poll.questions.concat(newQuestion);
  }

  onQuestionChanged(question: Question) {
    console.log(question);
  }
}
