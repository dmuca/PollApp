import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PollMainPageComponent } from './view/poll-main-page/poll-main-page.component';
import { UserListComponent } from './view/user-list/user-list.component';
import { UserRegisterComponent } from './view/user-register/user-register.component';
import {FormsModule} from '@angular/forms';
import {UserService} from './model/user.service';
import {HttpClientModule} from '@angular/common/http';

@NgModule({
  declarations: [
    AppComponent,
    PollMainPageComponent,
    UserListComponent,
    UserRegisterComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [UserService],
  bootstrap: [AppComponent]
})
export class AppModule { }
