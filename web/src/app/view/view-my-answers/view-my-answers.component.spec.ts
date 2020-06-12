import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewMyAnswersComponent } from './view-my-answers.component';

describe('ViewMyAnswersComponent', () => {
  let component: ViewMyAnswersComponent;
  let fixture: ComponentFixture<ViewMyAnswersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewMyAnswersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewMyAnswersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
