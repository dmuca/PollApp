import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UserListComponent} from './view/user-list/user-list.component';
import { RegisterComponent} from './view/register/register.component';
import {LoginComponent} from './view/login/login.component';
import {PollsComponent} from './view/polls/polls.component';
import {CreatePollComponent} from './view/create-poll/create-poll.component';
import {MyPollsComponent} from './view/my-polls/my-polls.component';

const routes: Routes = [
  { path: 'users', component: UserListComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'login', component: LoginComponent },
  { path: 'polls', component: PollsComponent },
  { path: 'createPoll', component: CreatePollComponent },
  { path: 'myPolls', component: MyPollsComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
