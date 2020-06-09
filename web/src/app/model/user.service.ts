import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {User} from './user';
import {Observable} from 'rxjs/Observable';
import {Subject} from 'rxjs';

@Injectable()
export class UserService {

  public usersList$: Subject<User[]> = new Subject<User[]>();
  public loggedInUser$: Subject<User> = new Subject();

  private url: string;

  constructor(private http: HttpClient) {
    this.url = 'http://93.180.178.64:2000/pollApp/';
  }

  public refreshUsersList() {
    this.http.get<User[]>(`${this.url}listUsers`).subscribe((usersList) => {
      this.usersList$.next(usersList);
    });
  }

  public save(user: User): Observable<User> {
    return this.http.post<User>(`${this.url}registerUser`, user);
  }

  public login(user: User) {
    return this.http.post<User>(`${this.url}login`, user).subscribe((loggedUser: User) => {
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
    return this.http.delete<User>(`${this.url}deleteUser`, httpOptions).subscribe((response) => {
      this.refreshUsersList();
    });
  }
}
