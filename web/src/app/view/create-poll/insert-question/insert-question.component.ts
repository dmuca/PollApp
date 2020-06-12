import {Component, Input, OnInit} from '@angular/core';
import {Question} from '../../../model/poll/question';
import {Answer} from '../../../model/poll/answer';

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

  addAnswer() {
    const newAnswer: Answer = {
      content: '',
    };
    this.question.answers = this.question.answers.concat(newAnswer);
  }

  removeAnswer() {
    this.question.answers.pop();
  }
}
