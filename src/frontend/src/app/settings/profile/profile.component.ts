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
  public nameForm: FormGroup;
  public jobForm: FormGroup;

  constructor(private activatedRoute: ActivatedRoute, private userService: UserService,
              private _formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.user = this.activatedRoute.snapshot.data.currentUser;

    let firstName = this.user.name.split(' ')[0];
    let secondName = this.user.name.split(' ')[1];
    let lastName = this.user.name.split(' ')[2];

    this.nameForm = this._formBuilder.group({
      firstName: [firstName, [Validators.maxLength(32), Validators.required]],
      secondName: [secondName, [Validators.maxLength(32), Validators.required]],
      lastName: [lastName, [Validators.maxLength(32), Validators.required]],
      phone: [this.user.phone, [Validators.minLength(10), Validators.maxLength(10), Validators.required]],
      birthday: ['', [Validators.required]],
      city: [this.user.city, [Validators.maxLength(255)]],
      about: [this.user.about, [Validators.maxLength(1024)]],
    });

    this.jobForm = this._formBuilder.group({
      post: [this.user.post, [Validators.required, Validators.maxLength(96)]]
    });
  }

  save() {
    if(this.nameForm.invalid)
      return

    return
  }
}
