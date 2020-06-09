import {Injectable} from '@angular/core';
import {Poll} from "./poll";
import {User} from "./user";
import {Subject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class PollService {
  public pollsList$: Subject<Poll[]> = new Subject<Poll[]>();

  constructor() {
  }

  public listPolls() {
    const fakePolls: Poll[] = [{
      name: 'Ankieta 1',
      status: 'Wypełniona',
    }, {
      name: 'Ankieta 2',
      status: 'Nowa',
    }];
    this.pollsList$.next(fakePolls);
  }
}
