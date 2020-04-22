import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PollMainPageComponent } from './poll-main-page.component';

describe('PollMainPageComponent', () => {
  let component: PollMainPageComponent;
  let fixture: ComponentFixture<PollMainPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PollMainPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PollMainPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
