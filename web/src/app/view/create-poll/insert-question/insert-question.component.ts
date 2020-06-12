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
  questions: Question[];

  @Input()
  questionIndex: number;

  constructor() {
  }

  ngOnInit(): void {
  }

  addAnswer() {
    const newAnswer: Answer = {
      content: '',
    };
    this.questions[this.questionIndex].answers = this.questions[this.questionIndex].answers.concat(newAnswer);
  }

  removeAnswer() {
    this.questions[this.questionIndex].answers.pop();
  }

  removeQuestion() {
    this.questions.splice(this.questionIndex, 1);
  }
}
