import {Injectable} from '@angular/core';
import {Poll} from './poll';
import {Observable, Subject} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {REST_API_URL} from '../../../common';

@Injectable({
  providedIn: 'root'
})
export class PollService {
  public pollsList$: Subject<Poll[]> = new Subject<Poll[]>();
  public myPollsList$: Subject<Poll[]> = new Subject<Poll[]>();

  constructor(private http: HttpClient) {
  }

  public listAllPolls() {
    this.http.get<Poll[]>(`${REST_API_URL}listPolls`).subscribe((polls: Poll[]) => {
      this.pollsList$.next(polls);
    });
  }

  public listMyPolls() {
    this.http.get<Poll[]>(`${REST_API_URL}listMyPolls`).subscribe((polls: Poll[]) => {
      this.myPollsList$.next(polls);
    });
  }

  public createPoll(poll: Poll): Observable<boolean> {
    return this.http.post<boolean>(`${REST_API_URL}createPoll`, poll);
  }
}
