import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PollMainPageComponent } from './view/poll-main-page/poll-main-page.component';
import { UserListComponent } from './view/user-list/user-list.component';
import { RegisterComponent } from './view/register/register.component';
import {FormsModule} from '@angular/forms';
import {UserService} from './model/user.service';
import {HttpClientModule} from '@angular/common/http';
import { LoginComponent } from './view/login/login.component';

@NgModule({
  declarations: [
    AppComponent,
    PollMainPageComponent,
    UserListComponent,
    RegisterComponent,
    LoginComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
  ],
  providers: [UserService],
  bootstrap: [AppComponent]
})
export class AppModule { }
