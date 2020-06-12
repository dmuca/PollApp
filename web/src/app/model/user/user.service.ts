import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {User} from './user';
import {Observable} from 'rxjs/Observable';
import {Subject} from 'rxjs';
import {REST_API_URL} from '../../../common';

@Injectable()
export class UserService {
  public usersList$: Subject<User[]> = new Subject<User[]>();

  constructor(private http: HttpClient) {
  }

  public getAll() {
    this.http.get<User[]>(`${REST_API_URL}listUsers`).subscribe((usersList) => {
      this.usersList$.next(usersList);
    });
  }

  public register(user: User): Observable<User> {
    return this.http.post<User>(`${REST_API_URL}registerUser`, user);
  }

  public delete(user: User) {
    const header: HttpHeaders = new HttpHeaders()
    .append('Content-Type', 'application/json; charset=UTF-8')
    .append('Authorization', 'Bearer ' + sessionStorage.getItem('accessToken'));
    const httpOptions = {
      headers: header,
      body: user
    };
    return this.http.delete<User>(`${REST_API_URL}deleteUser`, httpOptions).subscribe((response) => {
      this.getAll();
    });
  }
}
