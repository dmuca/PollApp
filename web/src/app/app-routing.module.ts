import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UserListComponent} from './view/user-list/user-list.component';
import { UserRegisterComponent} from './view/user-register/user-register.component';
import {UserLogInComponent} from './view/user-log-in/user-log-in.component';

const routes: Routes = [
  { path: 'users', component: UserListComponent },
  { path: 'registerUser', component: UserRegisterComponent },
  { path: 'login', component: UserLogInComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
