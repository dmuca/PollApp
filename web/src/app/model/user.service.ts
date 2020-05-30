import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {User} from './user';
import {Observable} from 'rxjs/Observable';

@Injectable()
export class UserService {

  private usersUrl: string;

  constructor(private http: HttpClient) {
    this.usersUrl = 'http://localhost:8080/pollApp/';
  }

  public findAll(): Observable<User[]> {
    return this.http.get<User[]>(`${this.usersUrl}usersList`);
  }

  public save(user: User): Observable<User> {
    return this.http.post<User>(`${this.usersUrl}createUser`, user);
  }

  public login(user: User): Observable<User> {
    return this.http.post<User>(`${this.usersUrl}login`, user);
  }
}
