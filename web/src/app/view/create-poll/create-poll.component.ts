import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute} from '@angular/router';

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

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.pollForm = this.formBuilder.group({
      pollName: ['', Validators.required],
    });
    this.returnUrl = this.route.snapshot.queryParams.returnUrl || '/';
  }

  onSubmit() {
    console.log('Submit poll');
  }

  get getFormControls() {
    return this.pollForm.controls;
  }
}
