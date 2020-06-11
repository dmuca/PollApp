import {Injectable} from '@angular/core';
import {Poll} from './poll';
import {Subject} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {REST_API_URL} from '../../../common';

@Injectable({
  providedIn: 'root'
})
export class PollService {
  public pollsList$: Subject<Poll[]> = new Subject<Poll[]>();

  constructor(private http: HttpClient) {
  }

  public listAllPolls() {
    this.http.get<Poll[]>(`${REST_API_URL}listPolls`).subscribe((polls) => {
      this.pollsList$.next(polls);
    });
  }
}
