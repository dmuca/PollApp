import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {User} from '../../model/user/user';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../../model/user/user.service';
import {AlertService} from '../../model/alert/alert.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AuthenticationService} from '../../model/authentication/authentication.service';
import {first} from 'rxjs/operators';

@Component({
  selector: 'app-user-log-in',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  loading = false;
  submitted = false;
  returnUrl: string;

  // user: User;

  constructor(
    private formBuilder: FormBuilder,
    private userService: UserService,
    private route: ActivatedRoute,
    private router: Router,
    private authenticationService: AuthenticationService,
    private alertService: AlertService
  ) {
    // this.user = new User();
    // redirect to home if already logged in
    if (this.authenticationService.currentUserValue) {
      this.router.navigate(['/']);
    }

    // this.userService.loggedInUser$.subscribe(loggedUser => {
    //   if (loggedUser) {
    //     sessionStorage.setItem('token', btoa(`${this.user.email}:${this.user.password}`));
    //     this.router.navigate(['/polls']);
    //   } else {
    //     sessionStorage.setItem('token', '');
    //     alertService.error('Błędne dane logowania, spróbuj jeszcze raz.');
    //   }
    // });
  }


  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      userEmail: ['', Validators.required],
      password: ['', Validators.required]
    });
    this.returnUrl = this.route.snapshot.queryParams.returnUrl || '/';
  }

  onSubmit() {
    this.alertService.clear();
    this.submitted = true;
    this.loading = true;

    const userEmail = this.getFormControls.userEmail.value;
    const password = this.getFormControls.password.value;
    this.authenticationService.login(userEmail, password)
    .pipe(first())
    .subscribe(
      data => {
        this.router.navigate([this.returnUrl]);
      },
      error => {
        this.alertService.error(error.error.message);
        this.loading = false;
      });
  }

  get getFormControls() {
    return this.loginForm.controls;
  }
}
