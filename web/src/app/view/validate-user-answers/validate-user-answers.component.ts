import { Component, OnInit } from '@angular/core';
import {PollService} from '../../model/poll/poll.service';
import {AlertService} from '../../model/alert/alert.service';

@Component({
  selector: 'app-validate-user-answers',
  templateUrl: './validate-user-answers.component.html',
  styleUrls: ['./validate-user-answers.component.scss']
})
export class ValidateUserAnswersComponent implements OnInit {
  isLoading: boolean;
  userAnswersValidationHashCode: number;

  constructor(private pollService: PollService, private alertService: AlertService) { }

  ngOnInit(): void {
  }

  validateUserAnswersHashCode() {
    this.isLoading = true;
    if (!this.userAnswersValidationHashCode){
      this.alertService.error('Wprowadź kod hash');
      this.isLoading = false;
      return;
    }
    else if (this.userAnswersValidationHashCode <= 0) {
      this.alertService.error('Kod hash nie pasuje do odpowiedzi');
    }
    else{
      this.alertService.success('Kod hash został zweryfikowany pomyślnie');
    }
    this.isLoading = false;
  }
}
