import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FillPollComponent } from './fill-poll.component';

describe('FillPollComponent', () => {
  let component: FillPollComponent;
  let fixture: ComponentFixture<FillPollComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FillPollComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FillPollComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
