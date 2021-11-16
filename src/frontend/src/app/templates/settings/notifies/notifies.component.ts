import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {User} from "../../../models/User";
import {ActivatedRoute} from "@angular/router";
import {SnackbarService} from "../../../shared/snackbar/snackbar.service";
import {UserService} from "../../../services/user.service";

@Component({
  selector: 'app-notifies',
  templateUrl: './notifies.component.html',
  styleUrls: ['./notifies.component.css']
})
export class NotifiesComponent implements OnInit {

  mainForm: FormGroup;
  currentUser: User;

  constructor(private formBuilder: FormBuilder, private activatedRoute: ActivatedRoute,
              private userService: UserService, private snackBar: SnackbarService) { }

  ngOnInit(): void {
    this.currentUser = this.activatedRoute.snapshot.data.currentUser;
    this.mainForm = this.formBuilder.group({
      disabledNotifies: [+localStorage.getItem('disabledNotice'), []],
      successDuration: [localStorage.getItem('successDuration'), []],
      errorDuration: [localStorage.getItem('errorDuration'), []],
      horizontalPosition: [localStorage.getItem('horizontalPosition'), []],
      verticalPosition: [localStorage.getItem('verticalPosition'), []],
    });
  }

  update() {
    if(this.mainForm.invalid)
      return;

    localStorage.setItem('disabledNotice', String(+this.mainForm.get('disabledNotifies').value));
    localStorage.setItem('successDuration', this.mainForm.get('successDuration').value);
    localStorage.setItem('errorDuration', this.mainForm.get('errorDuration').value);
    localStorage.setItem('horizontalPosition', this.mainForm.get('horizontalPosition').value);
    localStorage.setItem('verticalPosition', this.mainForm.get('verticalPosition').value);

    this.snackBar.success('Настройки уведомлений изменены!');
  }

  formatLabel(value: number) {
    if (value >= 1000) {
      return (value / 1000) + 'с';
    }
    return value + 'мс';
  }

}
