import {Component, Input, OnInit} from '@angular/core';
import {Question} from '../../../model/poll/question';

@Component({
  selector: 'app-insert-question',
  templateUrl: './insert-question.component.html',
  styleUrls: ['./insert-question.component.scss']
})
export class InsertQuestionComponent implements OnInit {
  @Input()
  question: Question;

  constructor() { }

  ngOnInit(): void {
  }

}
