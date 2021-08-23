import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../services/auth.service";
import {User} from "../models/User";

@Component({
  selector: 'app-authorization',
  templateUrl: './authorization.component.html',
  styleUrls: ['./authorization.component.css']
})
export class AuthorizationComponent implements OnInit {

  public mainForm: FormGroup;

  constructor(private authService: AuthService) { }

  ngOnInit(): void {
    this.mainForm = new FormGroup({
      email: new FormControl('', [Validators.email, Validators.maxLength(64), Validators.required]),
      password: new FormControl('', [Validators.minLength(6), Validators.maxLength(32), Validators.required]),
    });
  }

  auth() {
    if(this.mainForm.invalid)
      return

    let user = new User();
    user.username = this.mainForm.get('email').value;
    user.password = this.mainForm.get('password').value;

    this.authService.auth(user);
  }

}
