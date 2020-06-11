import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {User} from '../../model/user/user';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../../model/user/user.service';
import {AlertService} from '../../model/alert/alert.service';

@Component({
  selector: 'app-user-log-in',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  user: User;

  constructor(private route: ActivatedRoute,
              private router: Router, private userService: UserService, private alertService: AlertService) {
    this.user = new User();

    this.userService.loggedInUser$.subscribe(loggedUser => {
      if (loggedUser) {
        sessionStorage.setItem('token', btoa(`${this.user.email}:${this.user.password}`));
        this.router.navigate(['/polls']);
      } else {
        sessionStorage.setItem('token', '');
        alertService.error('Błędne dane logowania, spróbuj jeszcze raz.');
      }
    });
  }

  ngOnInit(): void {
    sessionStorage.setItem('token', '');
  }

  logIn(user: User) {
    this.userService.login(this.user);
  }
}
