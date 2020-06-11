import {Component, Input, OnInit} from '@angular/core';
import {Question} from '../../../model/poll/question';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AlertService} from '../../../model/alert/alert.service';

@Component({
  selector: 'app-insert-question',
  templateUrl: './insert-question.component.html',
  styleUrls: ['./insert-question.component.scss']
})
export class InsertQuestionComponent implements OnInit {
  @Input()
  question: Question;

  constructor() {
  }

  ngOnInit(): void {
  }

}
