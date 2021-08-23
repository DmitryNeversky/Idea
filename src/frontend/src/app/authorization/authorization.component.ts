import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-authorization',
  templateUrl: './authorization.component.html',
  styleUrls: ['./authorization.component.css']
})
export class AuthorizationComponent implements OnInit {

  public mainForm: FormGroup;

  constructor() { }

  ngOnInit(): void {
    this.mainForm = new FormGroup({
      email: new FormControl('', [Validators.email, Validators.maxLength(64), Validators.required]),
      password: new FormControl('', [Validators.minLength(6), Validators.maxLength(32), Validators.required]),
    });
  }

  auth() {
    if(this.mainForm.invalid)
      return
  }

}
