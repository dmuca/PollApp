import { Component, OnInit } from '@angular/core';
import {User} from '../../model/user';

@Component({
  selector: 'app-user-log-in',
  templateUrl: './user-log-in.component.html',
  styleUrls: ['./user-log-in.component.scss']
})
export class UserLogInComponent implements OnInit {

  user: User;
  isPasswordValid = true;

  constructor() {
    this.user = new User();
  }

  ngOnInit(): void {
  }

  logIn() {}
}
