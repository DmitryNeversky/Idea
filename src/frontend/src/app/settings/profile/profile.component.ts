import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {UserService} from "../../services/user.service";
import {User} from "../../models/User";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  public user: User;
  public personalForm: FormGroup;
  public jobForm: FormGroup;

  constructor(private activatedRoute: ActivatedRoute, private userService: UserService,
              private _formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.user = this.activatedRoute.snapshot.data.currentUser;

    let firstName = this.user.name.split(' ')[0];
    let secondName = this.user.name.split(' ')[1];
    let lastName = this.user.name.split(' ')[2];
    let birthday = Date.UTC(+this.user.birthday.split('.')[2],
        +this.user.birthday.split('.')[1],
        +this.user.birthday.split('.')[0]);

    this.personalForm = this._formBuilder.group({
      firstName: [firstName, [Validators.maxLength(32), Validators.required]],
      secondName: [secondName, [Validators.maxLength(32), Validators.required]],
      lastName: [lastName, [Validators.maxLength(32), Validators.required]],
      phone: [this.user.phone, [Validators.minLength(10), Validators.maxLength(11), Validators.required]],
      birthday: [new Date(birthday), [Validators.required]],
      city: [this.user.city, [Validators.maxLength(255)]],
      about: [this.user.about, [Validators.maxLength(1024)]],
    });

    this.jobForm = this._formBuilder.group({
      post: [this.user.post, [Validators.required, Validators.maxLength(96)]]
    });
  }

  save() {
    if(this.personalForm.invalid || this.jobForm.invalid)
      return

    let firstName = this.personalForm.get('firstName').value;
    firstName = firstName.charAt(0).toUpperCase() + firstName.substr(1).toLowerCase();
    let secondName = this.personalForm.get('secondName').value;
    secondName = secondName.charAt(0).toUpperCase() + secondName.substr(1).toLowerCase();
    let lastName = this.personalForm.get('lastName').value;
    lastName = lastName.charAt(0).toUpperCase() + lastName.substr(1).toLowerCase();

    let user = new User();
    user.name = firstName + " " + secondName + " " + lastName;
    user.phone = this.personalForm.get('phone').value;
    user.birthday = new Date(this.personalForm.get('birthday').value).toLocaleDateString();
    user.city = this.personalForm.get('city').value;
    user.about = this.personalForm.get('about').value;
    user.post = this.jobForm.get('post').value;
    console.log(user);
  }
}
