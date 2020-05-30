import {Component} from '@angular/core';
import {User} from './model/user';
import {UserService} from './model/user.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title: string;
  loggedUser: User = null;

  constructor(private userService: UserService) {
    this.title = 'PollApp - anonimowe ankiety';
    this.userService.getUser().subscribe(user => {
      if (user){
        this.title = `Witaj ${user.name}, dobrze Cię znów widzieć!`;
      }
      else if (this.loggedUser){
        this.title = `Zostałeś wylogowany, do zobaczenia ponownie!`;
      }
      this.loggedUser = user;
    });
  }

  logout() {
    this.userService.getUser().next(null);
  }
}
