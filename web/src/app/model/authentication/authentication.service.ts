import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {BehaviorSubject, Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {User} from '../user/user';


@Injectable({providedIn: 'root'})
export class AuthenticationService {
  private currentUserSubject: BehaviorSubject<User>;
  public currentUser: Observable<User>;
  private url: string;

  constructor(private http: HttpClient) {
    this.currentUserSubject = new BehaviorSubject<User>(JSON.parse(localStorage.getItem('currentUser')));
    this.currentUser = this.currentUserSubject.asObservable();
    this.url = 'http://93.180.178.64:2000/pollApp/';
  }

  public get currentUserValue(): User {
    return this.currentUserSubject.value;
  }

  login(user: User) {
    // TODO (Damian Muca): 6/11/20 configure config.apiUrl global constant.
    console.log('login user');
    console.log(user);
    return this.http.post<User>(`${this.url}login`, user)
    .pipe(map(loggedUser => {
      localStorage.setItem('currentUser', JSON.stringify(loggedUser));
      this.currentUserSubject.next(loggedUser);
      return loggedUser;
    }));
  }

  logout() {
    localStorage.removeItem('currentUser');
    this.currentUserSubject.next(null);
  }
}
