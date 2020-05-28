import {Component} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../../model/user.service';
import {User} from '../../model/user';

@Component({
  selector: 'app-user-form',
  templateUrl: './user-form.component.html',
  styleUrls: ['./user-form.component.scss']
})
export class UserFormComponent {

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
