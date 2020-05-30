import {Component, OnInit} from '@angular/core';
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
  isPasswordValid = true;
  displayInvalidLoginMessage = false;

  constructor(private route: ActivatedRoute,
              private router: Router, private userService: UserService) {
    this.user = new User();
  }

  ngOnInit(): void {
    sessionStorage.setItem('token', '');
  }

  logIn(user: User) {
    this.displayInvalidLoginMessage = false;
    // TODO (Damian Muca): 5/30/20 move to service ??
    this.userService.login(this.user).subscribe(loggedUser => {
      if (loggedUser) {
        sessionStorage.setItem('token', btoa(`${this.user.email}:${this.user.passwordHash}`));
        // TODO remove.
        console.log(`Welcome back ${loggedUser.name}`);
        // TODO (Damian Muca): 5/30/20 create component, navigate to pollslList
        this.router.navigate(['/users']);
      } else {
        this.displayInvalidLoginMessage = true;
      }
    });
  }
}
