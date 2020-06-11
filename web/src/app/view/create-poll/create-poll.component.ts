import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';

@Component({
  selector: 'app-create-poll',
  templateUrl: './create-poll.component.html',
  styleUrls: ['./create-poll.component.scss']
})
export class CreatePollComponent implements OnInit {
  pollForm: FormGroup;

  constructor(
    private formBuilder: FormBuilder) {
  }

  ngOnInit(): void {
  }

  onSubmit() {
    console.log('Submit poll');
  }
}
