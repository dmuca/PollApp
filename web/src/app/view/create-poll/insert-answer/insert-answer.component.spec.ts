import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InsertAnswerComponent } from './insert-answer.component';

describe('InsertAnswerComponent', () => {
  let component: InsertAnswerComponent;
  let fixture: ComponentFixture<InsertAnswerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InsertAnswerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InsertAnswerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
