import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {MatDialog} from "@angular/material/dialog";
import {User} from "../../../models/User";
import {UserService} from "../../../services/user.service";
import {DialogComponent} from "../../../shared/dialog/dialog.component";
import {AuthService} from "../../../services/auth.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {HttpErrorResponse} from "@angular/common/http";
import {SnackbarService} from "../../../shared/snackbar/snackbar.service";

@Component({
  selector: 'app-secure',
  templateUrl: './secure.component.html',
  styleUrls: ['./secure.component.css']
})
export class SecureComponent implements OnInit {

  hide1: boolean = true;
  hide2: boolean = true;
  passwordError: string;

  public user: User;

  public emailForm: FormGroup;
  public passwordForm: FormGroup;

  constructor(private userService: UserService,
              private authService: AuthService,
              private _formBuilder: FormBuilder,
              private activatedRoute: ActivatedRoute,
              private router: Router,
              private snackBar: SnackbarService,
              private _dialog: MatDialog) { }

  ngOnInit(): void {
    this.user = this.activatedRoute.snapshot.data.currentUser;
    this.emailForm = this._formBuilder.group({
      email: ['', [Validators.email, Validators.maxLength(64)]],
    });
    this.passwordForm = this._formBuilder.group({
      oldPassword: ['', [Validators.maxLength(32), Validators.required]],
      newPassword: ['', [Validators.minLength(6), Validators.maxLength(32), Validators.required]],
    });
  }

  delete() {
    this._dialog.open(DialogComponent, {
      data: {
        title: "Подтвердите действие",
        message: "Восстановить аккаунт не получится. Вы уверены что хотите удалить его?"
      }
    }).afterClosed().subscribe(result => {
      if(result) {
        this.userService.deleteUser(this.user.id).subscribe(() => {
          this.authService.logout();
          this.snackBar.success('Пользователь удален.');
        }, error => {
          console.log(error);
          this.snackBar.error();
        });
      }
    });
  }

  changePassword() {
    if(!this.passwordForm.valid)
      return;

    const formData = new FormData();
    formData.append('oldPassword', this.passwordForm.get('oldPassword').value);
    formData.append('newPassword', this.passwordForm.get('newPassword').value);

    this.userService.changePassword(formData).subscribe(() => {
      this.authService.logout();
      this.snackBar.success('Пароль успешно изменен!');
      this.passwordError = null;
    },(error: HttpErrorResponse) => {
      console.log(error);
      this.passwordError = error.error;
    });
  }
}
