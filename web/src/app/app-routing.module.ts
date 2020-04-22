import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {PollMainPageComponent} from './poll-main-page/poll-main-page.component';


const routes: Routes = [{
  path: '',
  component: PollMainPageComponent,
}, {
  path: 'PollApp',
  component: PollMainPageComponent,
},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  declarations: []
})
export class AppRoutingModule {
}
