import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {User} from './user';
import {Observable} from 'rxjs/Observable';
import {Subject} from 'rxjs';

@Injectable()
export class UserService {

  public usersList$: Subject<User[]> = new Subject<User[]>();
  public loggedInUser$: Subject<User> = new Subject();

  private usersUrl: string;

  constructor(private http: HttpClient) {
    this.usersUrl = 'http://93.180.178.64:2000/pollApp/';
  }

  public refreshUsersList() {
    this.http.get<User[]>(`${this.usersUrl}listUsers`).subscribe((usersList) => {
      this.usersList$.next(usersList);
    });
  }

  public save(user: User): Observable<User> {
    return this.http.post<User>(`${this.usersUrl}registerUser`, user);
  }

  public login(user: User) {
    return this.http.post<User>(`${this.usersUrl}login`, user).subscribe((loggedUser: User) => {
      this.loggedInUser$.next(loggedUser);
    });
  }

  public delete(user: User) {
    const header: HttpHeaders = new HttpHeaders()
    .append('Content-Type', 'application/json; charset=UTF-8')
    .append('Authorization', 'Bearer ' + sessionStorage.getItem('accessToken'));
    const httpOptions = {
      headers: header,
      body: user
    };
    return this.http.delete<User>(`${this.usersUrl}deleteUser`, httpOptions).subscribe((response) => {
      this.refreshUsersList();
    });
  }
}
