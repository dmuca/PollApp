import {Injectable} from '@angular/core';
import {Poll} from './poll';
import {Subject} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {PollState} from "./PollState";

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
      pollId: 1,
      name: 'Ankieta 1',
      status: PollState.Filled,
    }, {
      pollId: 1,
      name: 'Ankieta 2',
      status: PollState.New,
    }];
    this.pollsList$.next(fakePolls);
    this.http.get<Poll[]>(`${this.url}listPolls`).subscribe((polls) => {
      this.pollsList$.next(polls.concat(fakePolls));
    });
  }
}
