import {Component} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../../model/user.service';
import {User} from '../../model/user';

@Component({
  selector: 'app-user-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {

  user: User;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private userService: UserService) {
    this.user = new User();
  }

  onSubmit() {
    console.log('submit');
    this.userService.save(this.user).subscribe(result => {
      console.log('result');
      console.log(result);
      this.gotoUserList();
    });
  }

  gotoUserList() {
    this.router.navigate(['/users']);
  }
}
