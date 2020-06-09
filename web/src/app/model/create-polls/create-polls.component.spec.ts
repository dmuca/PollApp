import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreatePollsComponent } from './create-polls.component';

describe('CreatePollsComponent', () => {
  let component: CreatePollsComponent;
  let fixture: ComponentFixture<CreatePollsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreatePollsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreatePollsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
