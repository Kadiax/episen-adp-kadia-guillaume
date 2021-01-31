import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ImageconvComponent } from './imageconv.component';

describe('ImageconvComponent', () => {
  let component: ImageconvComponent;
  let fixture: ComponentFixture<ImageconvComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ImageconvComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ImageconvComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
