import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../../model/user/user.service';
import {User} from '../../model/user/user';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AuthenticationService} from '../../model/authentication/authentication.service';
import {AlertService} from '../../model/alert/alert.service';
import {first} from 'rxjs/operators';

@Component({
  selector: 'app-user-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  registerForm: FormGroup;
  loading = false;
  submitted = false;

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private authenticationService: AuthenticationService,
    private userService: UserService,
    private alertService: AlertService
  ) {
    if (this.authenticationService.currentUserValue) {
      this.router.navigate(['/']);
    }
  }

  ngOnInit() {
    this.registerForm = this.formBuilder.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['', Validators.required],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  get getFormControls() { return this.registerForm.controls; }

  onSubmit() {
    this.submitted = true;
    this.alertService.clear();
    if (this.registerForm.invalid) {
      return;
    }
    this.loading = true;

    this.userService.register(this.registerForm.value)
    .pipe(first())
    .subscribe(
      data => {
        this.alertService.success('Registration successful', true);
        this.router.navigate(['/register']);
      },
      error => {
        this.alertService.error(error);
        this.loading = false;
      });
  }
}
