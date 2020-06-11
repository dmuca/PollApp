import {Injectable} from '@angular/core';
import {Poll} from './poll';
import {Subject} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {PollState} from "./poll.state";

@Injectable({
  providedIn: 'root'
})
export class PollService {
  public pollsList$: Subject<Poll[]> = new Subject<Poll[]>();

  private url: string;

  constructor(private http: HttpClient) {
    this.url = 'http://93.180.178.64:2000/pollApp/';
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
    this.http.get<Poll[]>(`${this.url}listPolls`).subscribe((polls) => {
      console.log(...polls);
      this.pollsList$.next(polls.concat(fakePolls));
    });
  }
}
