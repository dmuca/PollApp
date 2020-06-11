import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-insert-answer',
  templateUrl: './insert-answer.component.html',
  styleUrls: ['./insert-answer.component.scss']
})
export class InsertAnswerComponent implements OnInit {
  @Input()
  answer: string;

  constructor() { }

  ngOnInit(): void {
  }

}
