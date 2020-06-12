import {Component, Input, OnInit} from '@angular/core';
import {Answer} from '../../../model/poll/answer';

@Component({
  selector: 'app-insert-answer',
  templateUrl: './insert-answer.component.html',
  styleUrls: ['./insert-answer.component.scss']
})
export class InsertAnswerComponent implements OnInit {
  @Input()
  answer: Answer;

  constructor() { }

  ngOnInit(): void {
  }
}
