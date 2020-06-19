import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ValidateUserAnswersComponent } from './validate-user-answers.component';

describe('ValidateUserAnswersComponent', () => {
  let component: ValidateUserAnswersComponent;
  let fixture: ComponentFixture<ValidateUserAnswersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ValidateUserAnswersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ValidateUserAnswersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
