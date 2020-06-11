import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute} from '@angular/router';
import {User} from '../../model/user/user';
import {first} from 'rxjs/operators';
import {AlertService} from '../../model/alert/alert.service';
import {Question} from '../../model/poll/question';

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
  questions: Question [] = [{
    title: 'xdd',
    answers: ['nie', 'tak', 'może']
  }];

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
    this.submitted = true;
    this.alertService.clear();
    if (this.pollForm.invalid) {
      return;
    }
    this.loading = true;
  }

  get getFormControls() {
    return this.pollForm.controls;
  }

  addQuestion() {
    const newQuestion: Question = new Question();
    this.questions = this.questions.concat(newQuestion);
  }
}
