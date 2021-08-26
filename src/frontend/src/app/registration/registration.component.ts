import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {StepperOrientation} from "@angular/cdk/stepper";
import {User} from "../models/User";
import {AuthService} from "../services/auth.service";

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  hide: boolean = true;
  preloader: boolean = false;

  firstFormGroup: FormGroup;
  secondFormGroup: FormGroup;
  finalFormGroup: FormGroup;
  orientation: StepperOrientation = "horizontal";

  constructor(private _formBuilder: FormBuilder,
              private authService: AuthService) {}

  ngOnInit() {
    if(window.screen.width <= 600) this.orientation = "vertical";

    this.firstFormGroup = this._formBuilder.group({
      firstName: ['', [Validators.maxLength(32), Validators.required]],
      secondName: ['', [Validators.maxLength(32), Validators.required]],
      lastName: ['', [Validators.maxLength(32), Validators.required]],
      phone: ['', [Validators.minLength(11), Validators.maxLength(11), Validators.required]],
      birthday: ['', [Validators.required]],
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

  registration() {
    if(this.secondFormGroup.invalid)
      return

    let user = new User();
    user.username = this.secondFormGroup.get('email').value;
    user.password = this.secondFormGroup.get('password').value;
    user.name = this.firstFormGroup.get('firstName').value
        + " " + this.firstFormGroup.get('secondName').value
        + " " + this.firstFormGroup.get('lastName').value;
    user.birthday = this.firstFormGroup.get('birthday').value;
    user.post = this.firstFormGroup.get('post').value;

    this.preloader = true;

    this.authService.registration(user).subscribe(() => {
      this.preloader = false;
    }, error => {
      console.log(error);
      this.preloader = false;
    });
  }

}
