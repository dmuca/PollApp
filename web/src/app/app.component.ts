import {Component} from '@angular/core';
import {User} from './model/user/user';
import {UserService} from './model/user/user.service';
import {AuthenticationService} from './model/authentication/authentication.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title: string;
  loggedUser: User = null;

  constructor(private userService: UserService, private authenticationService: AuthenticationService) {
    this.title = 'PollApp - anonimowe ankiety';
    this.authenticationService.currentUser.subscribe(user => {
      if (user){
        this.title = `Witaj ${user.firstName}, dobrze Cię znów widzieć!`;
      }
      else if (this.loggedUser){
        this.title = `PollApp - anonimowe ankiety`;
      }
      this.loggedUser = user;
    });
  }

  logout() {
    this.authenticationService.logout();
  }

  isUserLoggedIn(){
    return this.authenticationService.currentUserValue;
  }
}
