import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UserListComponent} from './view/user-list/user-list.component';
import { RegisterComponent} from './view/register/register.component';
import {LoginComponent} from './view/login/login.component';

const routes: Routes = [
  { path: 'users', component: UserListComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'login', component: LoginComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
