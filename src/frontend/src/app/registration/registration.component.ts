import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {StepperOrientation} from "@angular/cdk/stepper";

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  firstFormGroup: FormGroup;
  secondFormGroup: FormGroup;
  finalFormGroup: FormGroup;
  orientation: StepperOrientation = "horizontal";

  constructor(private _formBuilder: FormBuilder) {}

  ngOnInit() {
    if(window.screen.width <= 600) this.orientation = "vertical";

    this.firstFormGroup = this._formBuilder.group({
      firstName: ['', [Validators.maxLength(32), Validators.required]],
      secondName: ['', [Validators.maxLength(32), Validators.required]],
      lastName: ['', [Validators.maxLength(32), Validators.required]],
      phone: ['', [Validators.minLength(11), Validators.maxLength(11), Validators.required]],
      birthday: [new Date(2, 1, 19), [Validators.required]],
      sex: ['', [Validators.required]],
      post: ['', [Validators.required, Validators.maxLength(64)]]
    });
    this.secondFormGroup = this._formBuilder.group({
      email: ['', [Validators.email, Validators.maxLength(64), Validators.required]],
      password: ['', [Validators.minLength(6), Validators.maxLength(32), Validators.required]],
    });
    this.finalFormGroup = this._formBuilder.group({
      code: ['', [Validators.required, Validators.maxLength(64)]]
    });
  }

}
