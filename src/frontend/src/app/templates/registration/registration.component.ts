import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {StepperOrientation} from "@angular/cdk/stepper";
import {User} from "../../models/User";
import {AuthService} from "../../services/auth.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Post} from "../../models/Post";

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  hide: boolean = true;
  preloader: boolean = false;
  error: string = "";

  firstFormGroup: FormGroup;
  secondFormGroup: FormGroup;
  finalFormGroup: FormGroup;
  orientation: StepperOrientation = "horizontal";

  posts: Post[];

  constructor(private _formBuilder: FormBuilder,
              private authService: AuthService,
              private router: Router,
              private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    if(window.screen.width <= 600) this.orientation = "vertical";

    this.posts = this.activatedRoute.snapshot.data.posts;

    this.firstFormGroup = this._formBuilder.group({
      firstName: ['', [Validators.maxLength(32), Validators.required]],
      secondName: ['', [Validators.maxLength(32), Validators.required]],
      lastName: ['', [Validators.maxLength(32), Validators.required]],
      phone: ['', [Validators.minLength(10), Validators.maxLength(10), Validators.required]],
      birthday: ['', [Validators.required]],
      post: ['', [Validators.required, Validators.maxLength(96)]]
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
    // if(this.firstFormGroup.invalid || this.secondFormGroup.invalid)
    //   return

    let firstName = this.firstFormGroup.get('firstName').value;
    firstName = firstName.charAt(0).toUpperCase() + firstName.substr(1).toLowerCase();
    let secondName = this.firstFormGroup.get('secondName').value;
    secondName = secondName.charAt(0).toUpperCase() + secondName.substr(1).toLowerCase();
    let lastName = this.firstFormGroup.get('lastName').value;
    lastName = lastName.charAt(0).toUpperCase() + lastName.substr(1).toLowerCase();

    let user = new User();
    user.username = this.secondFormGroup.get('email').value;
    user.password = this.secondFormGroup.get('password').value;
    user.name = firstName + " " + secondName + " " + lastName;
    user.phone = this.firstFormGroup.get('phone').value;
    user.birthday = this.firstFormGroup.get('birthday').value;
    user.post = this.firstFormGroup.get('post').value;

    this.preloader = true;

    this.authService.registration(user).subscribe(() => {
      this.preloader = false;
      this.router.navigate(['/auth']);
    }, error => {
      console.log(error);
      this.preloader = false;
    });
  }

}
