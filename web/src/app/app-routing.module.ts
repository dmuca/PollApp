import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {UserListComponent} from './view/user-list/user-list.component';
import {RegisterComponent} from './view/register/register.component';
import {LoginComponent} from './view/login/login.component';
import {AllPollsComponent} from './view/polls/all-polls.component';
import {CreatePollComponent} from './view/create-poll/create-poll.component';
import {MyPollsComponent} from './view/my-polls/my-polls.component';
import {AuthGuard} from './model/authentication/auth.guard';
import {FillPollComponent} from './view/fill-poll/fill-poll.component';
import {ViewMyAnswersComponent} from './view/view-my-answers/view-my-answers.component';
import {ViewAnswersToMyPollComponent} from './view/view-answers-to-my-poll/view-answers-to-my-poll.component';

const routes: Routes = [
  // {path: 'users', component: UserListComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'login', component: LoginComponent},
  {path: 'myPolls', component: MyPollsComponent, canActivate: [AuthGuard]},
  {path: 'allPolls', component: AllPollsComponent, canActivate: [AuthGuard]},
  {
    path: 'createPoll',
    component: CreatePollComponent,
    canActivate: [AuthGuard]
  },
  {path: 'fillPoll', component: FillPollComponent, canActivate: [AuthGuard]},
  {
    path: 'viewMyAnswers',
    component: ViewMyAnswersComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'viewAnswersToMyPoll',
    component: ViewAnswersToMyPollComponent,
    canActivate: [AuthGuard]
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
