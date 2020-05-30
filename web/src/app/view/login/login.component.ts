import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {User} from '../../model/user';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../../model/user.service';

@Component({
  selector: 'app-user-log-in',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  user: User;
  isInvaligLoginWarrningHidden = true;

  constructor(private route: ActivatedRoute,
              private router: Router, private userService: UserService) {
    this.user = new User();

    this.userService.getUser().subscribe(loggedUser => {
      if (loggedUser) {
        sessionStorage.setItem('token', btoa(`${this.user.email}:${this.user.passwordHash}`));
        this.router.navigate(['/polls']);
      } else {
        sessionStorage.setItem('token', '');
        this.isInvaligLoginWarrningHidden = false;
      }
    });
  }

  ngOnInit(): void {
    sessionStorage.setItem('token', '');
  }

  logIn(user: User) {
    this.isInvaligLoginWarrningHidden = true;
    this.userService.login(this.user);
  }
}
