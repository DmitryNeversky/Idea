import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {MatSnackBar} from "@angular/material/snack-bar";
import {MatDialog} from "@angular/material/dialog";
import {User} from "../../../models/User";
import {UserService} from "../../../services/user.service";
import {DialogComponent} from "../../../shared/dialog/dialog.component";
import {SnackbarComponent} from "../../../shared/snackbar/snackbar.component";
import {AuthService} from "../../../services/auth.service";

@Component({
  selector: 'app-secure',
  templateUrl: './secure.component.html',
  styleUrls: ['./secure.component.css']
})
export class SecureComponent implements OnInit {

  public user: User;

  constructor(private userService: UserService,
              private authService: AuthService,
              private activatedRoute: ActivatedRoute,
              private router: Router,
              private _snackBar: MatSnackBar,
              private _dialog: MatDialog) { }

  ngOnInit(): void {
    this.user = this.activatedRoute.snapshot.data.currentUser;
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
          this._snackBar.openFromComponent(SnackbarComponent, {
            duration: 2000,
            horizontalPosition: "start",
            data: "Пользователь удален."
          });
        }, error => {
          console.log(error);
          this._snackBar.openFromComponent(SnackbarComponent, {
            duration: 3000,
            horizontalPosition: "start",
            data: "Произошла ошибка, попробуйте позже."
          });
        });
      }
    });
  }

}
