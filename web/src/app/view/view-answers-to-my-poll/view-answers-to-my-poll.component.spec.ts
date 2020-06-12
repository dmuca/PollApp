import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewAnswersToMyPollComponent } from './view-answers-to-my-poll.component';

describe('ViewAnswersToMyPollComponent', () => {
  let component: ViewAnswersToMyPollComponent;
  let fixture: ComponentFixture<ViewAnswersToMyPollComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewAnswersToMyPollComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewAnswersToMyPollComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
