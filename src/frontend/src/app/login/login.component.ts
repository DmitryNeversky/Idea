import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../services/auth.service";
import {Router} from "@angular/router";
import {HttpErrorResponse, HttpStatusCode} from "@angular/common/http";

@Component({
  selector: 'app-authorization',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  preloader: boolean = false;
  error: string = "";

  public mainForm: FormGroup;

  constructor(private authService: AuthService,
              private router: Router) { }

  ngOnInit(): void {
    this.mainForm = new FormGroup({
      email: new FormControl('', [Validators.email, Validators.maxLength(64), Validators.required]),
      password: new FormControl('', [Validators.maxLength(32), Validators.required]),
    });
  }

  login() {
    if(this.mainForm.invalid)
      return

    const formData = new FormData();
    formData.append('username', this.mainForm.get('email').value);
    formData.append('password', this.mainForm.get('password').value);

    this.preloader = true;

    this.authService.login(formData).subscribe(() => {
      this.preloader = false;
      this.router.navigate(['/']);
    }, (error: HttpErrorResponse) => {
      console.log(error);
      if(error.status == HttpStatusCode.Forbidden)
        this.error = "Пользователь с такими данными не найден.";
      else if(error.status >= 500)
        this.error = "Произошла внутренняя ошибка. Попробуйте повторить действие позже.";
      else this.error = "Произошел сбой. Попробуйте повторить действие позже.";
      this.preloader = false;
    });
  }

}
