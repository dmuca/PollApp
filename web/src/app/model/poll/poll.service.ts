import {Injectable} from '@angular/core';
import {Poll} from './poll';
import {Observable, Subject} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {REST_API_URL} from '../../../common';
import {UserAnswer} from './user.answer';
import {UserAnswerValidator} from './user.answer.validator';
import {User} from '../user/user';

@Injectable({
  providedIn: 'root'
})
export class PollService {
  public pollsList$: Subject<Poll[]> = new Subject<Poll[]>();
  public myPollsList$: Subject<Poll[]> = new Subject<Poll[]>();
  public pollDetails$: Subject<Poll> = new Subject<Poll>();
  public usersWhoAnsweredPoll$: Subject<User[]> = new Subject<User[]>();
  public usersWhoDidNotAnswerPoll$: Subject<User[]> = new Subject<User[]>();

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

  public getPollDetails(pollId: number) {
    return this.http.get<Poll>(`${REST_API_URL}getPollDetails/${pollId}`).subscribe((pollDetails: Poll) => {
      this.pollDetails$.next(pollDetails);
    });
  }

  public saveAnswers(userAnswers: UserAnswer[]): Observable<number> {
    return this.http.post<number>(`${REST_API_URL}saveUserAnswers`, userAnswers);
  }

  public verifyPollAnswers(userAnswerValidator: UserAnswerValidator): Observable<boolean> {
    return this.http.post<boolean>(`${REST_API_URL}verifyPollAnswers`, userAnswerValidator);
  }

  getUsersWhoAnsweredToPoll(pollId: number) {
    const users: User[] = [{
      id: 0,
      firstName: 'Kasia',
      lastName: 'Kowalska',
      password: 'unknown',
      email: 'Kasia@wp.pl',
      token: 'unknown',
    }, {
      id: 1,
      firstName: 'Bartosz',
      lastName: 'Pliżga',
      password: 'unknown',
      email: 'BP@wp.pl',
      token: 'unknown',
    }
    ];
    console.log(users);
    this.usersWhoAnsweredPoll$.next(users);
  }

  getUsersWhoDidNotAnswerToPoll(pollId: number) {
    const users: User[] = [{
      id: 2,
      firstName: 'Aleks',
      lastName: 'Maslana',
      password: 'unknown',
      email: 'AKKKK@wp.pl',
      token: 'unknown',
    }, {
      id: 3,
      firstName: 'Konieczko',
      lastName: 'Konieczny',
      password: 'unknown',
      email: 'koniecznie@wp.pl',
      token: 'unknown',
    }
    ];
    console.log(users);
    this.usersWhoDidNotAnswerPoll$.next(users);
  }
}
