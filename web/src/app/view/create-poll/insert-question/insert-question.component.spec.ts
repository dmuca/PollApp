import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InsertQuestionComponent } from './insert-question.component';

describe('InsertQuestionComponent', () => {
  let component: InsertQuestionComponent;
  let fixture: ComponentFixture<InsertQuestionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InsertQuestionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InsertQuestionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
