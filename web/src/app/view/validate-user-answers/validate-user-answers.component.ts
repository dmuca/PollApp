import {Component, OnInit} from '@angular/core';
import {PollService} from '../../model/poll/poll.service';
import {AlertService} from '../../model/alert/alert.service';
import {UserAnswerValidator} from '../../model/poll/user.answer.validator';
import {Poll} from '../../model/poll/poll';
import {isFiniteInteger} from 'smart-buffer/typings/utils';

@Component({
  selector: 'app-validate-user-answers',
  templateUrl: './validate-user-answers.component.html',
  styleUrls: ['./validate-user-answers.component.scss']
})
export class ValidateUserAnswersComponent implements OnInit {
  isLoading: boolean;
  userAnswerValidator = new UserAnswerValidator();

  constructor(private pollService: PollService, private alertService: AlertService) {
  }

  ngOnInit(): void {
    this.pollService.pollDetails$.subscribe((pollDetails: Poll) => {
      this.userAnswerValidator.pollId = pollDetails.pollId;
    });
  }

  validateUserAnswersHashCode() {
    this.isLoading = true;
    if (!this.userAnswerValidator.validationHashCode) {
      this.alertService.error('Wprowadź kod hash');
      this.isLoading = false;
      return;
    }
    else if (isNaN(this.userAnswerValidator.validationHashCode)){
      this.alertService.error('Wprowadzony hash kod nie jest numerem');
      this.isLoading = false;
      return;
    }
    else if (this.userAnswerValidator.validationHashCode > 2147483647){
      this.alertService.error('Wprowadzony numer jest zbyt duży');
      this.isLoading = false;
      return;
    }

    this.pollService.verifyPollAnswers(this.userAnswerValidator).subscribe((isHashCodeCorrect) => {
      if (isHashCodeCorrect) {
        this.alertService.success('Kod hash został zweryfikowany pomyślnie');
      } else {
        this.alertService.error('Kod hash nie pasuje do odpowiedzi');
      }
      this.isLoading = false;
    });
  }
}
