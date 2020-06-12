import {PollState} from './poll.state';
import {Question} from './question';

export class Poll{
  pollId: number;
  name: string;
  questions: Question[];
  state: PollState;
}
