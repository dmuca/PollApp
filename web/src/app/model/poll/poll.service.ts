import {Injectable} from '@angular/core';
import {Poll} from './poll';
import {Subject} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {PollState} from './poll.state';
import {REST_API_URL} from '../../../common';

@Injectable({
  providedIn: 'root'
})
export class PollService {
  public pollsList$: Subject<Poll[]> = new Subject<Poll[]>();

  constructor(private http: HttpClient) {
  }

  public listAllPolls() {
    const fakePolls: Poll[] = [{
      pollId: 5,
      name: 'Ankieta 1',
      state: PollState.Filled,
    }, {
      pollId: 6,
      name: 'Ankieta 2',
      state: PollState.New,
    }];
    this.pollsList$.next(fakePolls);
    this.http.get<Poll[]>(`${REST_API_URL}listPolls`).subscribe((polls) => {
      this.pollsList$.next(polls.concat(fakePolls));
    });
  }
}
