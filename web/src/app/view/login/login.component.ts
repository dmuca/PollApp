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

  constructor(
    private formBuilder: FormBuilder,
    private userService: UserService,
    private route: ActivatedRoute,
    private router: Router,
    private authenticationService: AuthenticationService,
    private alertService: AlertService
  ) {
    if (this.authenticationService.currentUserValue) {
      this.router.navigate(['/']);
    }
  }


  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      userEmail: ['', Validators.required],
      password: ['', Validators.required]
    });
    this.returnUrl = this.route.snapshot.queryParams.returnUrl || '/';
  }

  onSubmit() {
    this.submitted = true;
    this.alertService.clear();
    if (this.loginForm.invalid) {
      return;
    }
    this.loading = true;

    const user: User = new User();
    user.email = this.getFormControls.userEmail.value;
    user.password = this.getFormControls.password.value;

    this.authenticationService.login(user)
    .pipe(first())
    .subscribe(
      data => {
        this.router.navigate(['/allPolls']);
      },
      error => {
        this.alertService.error(error);
        this.loading = false;
      });
  }

  get getFormControls() {
    return this.loginForm.controls;
  }
}
