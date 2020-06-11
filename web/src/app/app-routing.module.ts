import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {UserListComponent} from './view/user-list/user-list.component';
import {RegisterComponent} from './view/register/register.component';
import {LoginComponent} from './view/login/login.component';
import {AllPollsComponent} from './view/polls/all-polls.component';
import {CreatePollComponent} from './view/create-poll/create-poll.component';
import {MyPollsComponent} from './view/my-polls/my-polls.component';
import {AuthGuard} from './model/authentication/auth.guard';

const routes: Routes = [
  {path: '', redirectTo: 'users', pathMatch: 'full'},
  {path: 'users', component: UserListComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'login', component: LoginComponent},
  {path: 'myPolls', component: MyPollsComponent, canActivate: [AuthGuard]},
  {path: 'allPolls', component: AllPollsComponent, canActivate: [AuthGuard]},
  {path: 'createPoll', component: CreatePollComponent, canActivate: [AuthGuard]},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
