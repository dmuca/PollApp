import {Component, OnInit} from '@angular/core';
import {User} from '../../model/user';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../../model/user.service';
import {map} from 'rxjs/operators';

@Component({
  selector: 'app-user-log-in',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  user: User;
  isPasswordValid = true;

  // constructor(private route: ActivatedRoute,
  //             private router: Router,
  constructor(private userService: UserService) {
    this.user = new User();
  }

  ngOnInit(): void {
    sessionStorage.setItem('token', '');
  }

  logIn(user: User) {
    // TODO (Damian Muca): 5/30/20 move to service ??
    this.userService.login(this.user).subscribe(x => console.log(x));
    // .subscribe(isValid => {
    //   if (isValid) {
    //     sessionStorage.setItem('token', btoa(this.user.email + ':' + this.user.passwordHash));
    //     // TODO (Damian Muca): 5/30/20 create component, navigate to pollslList
    //     this.router.navigate(['/users']);
    //   } else {
    //     alert('Nieprawidłowe dane, spróbuj ponownie.');
    //   }
    // });
  }
}
