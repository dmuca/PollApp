import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {User} from './user';
import {Observable} from 'rxjs/Observable';
import {Subject} from 'rxjs';

@Injectable()
export class UserService {

  private usersUrl: string;
  private user$: Subject<User> = new Subject();

  constructor(private http: HttpClient) {
    this.usersUrl = 'http://localhost:8080/pollApp/';
  }

  getUser(): Subject<User> {
    return this.user$;
  }

  public findAll(): Observable<User[]> {
    return this.http.get<User[]>(`${this.usersUrl}usersList`);
  }

  public save(user: User): Observable<User> {
    return this.http.post<User>(`${this.usersUrl}createUser`, user);
  }

  public login(user: User) {
    return this.http.post<User>(`${this.usersUrl}login`, user).subscribe((loggedUser: User) => {
      this.user$.next(loggedUser);
    });
  }

  public delete(user: User) {
    const header: HttpHeaders = new HttpHeaders()
    .append('Content-Type', 'application/json; charset=UTF-8')
    .append('Authorization', 'Bearer ' + sessionStorage.getItem('accessToken'));
    const httpOptions = {
      headers: header,
      body: { User: user }
    };
    console.log('delete request...');
    return this.http.delete<User>(`${this.usersUrl}deleteUser`, httpOptions);
  }
}
