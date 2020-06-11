import {Component, OnInit} from '@angular/core';
import {User} from '../../model/user/user';
import {UserService} from '../../model/user/user.service';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.scss']
})
export class UserListComponent implements OnInit {
  users: User[];

  constructor(private userService: UserService) {
    this.userService.usersList$.subscribe((users: User[]) => {
      this.users = users;
    });
  }

  ngOnInit(): void {
    this.userService.refreshUsersList();
  }

  deleteUser(user: User) {
    this.userService.delete(user);
  }
}
