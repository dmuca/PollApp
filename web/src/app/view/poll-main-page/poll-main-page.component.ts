import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-poll-main-page',
  templateUrl: './poll-main-page.component.html',
  styleUrls: ['./poll-main-page.component.scss'],
})
export class PollMainPageComponent implements OnInit {
  title: string;

  constructor() {
    this.title = 'Anonymous Poll Application';
  }

  ngOnInit(): void {
  }

}
