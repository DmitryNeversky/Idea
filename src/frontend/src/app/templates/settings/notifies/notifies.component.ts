import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {User} from "../../../models/User";
import {ActivatedRoute} from "@angular/router";
import {Settings} from "../../../models/Settings";
import {UserService} from "../../../services/user.service";
import {SnackbarService} from "../../../shared/snackbar/snackbar.service";
import {NoticeSetting} from "../../../models/settings/NoticeSetting";

@Component({
  selector: 'app-notifies',
  templateUrl: './notifies.component.html',
  styleUrls: ['./notifies.component.css']
})
export class NotifiesComponent implements OnInit {

  mainForm: FormGroup;
  currentUser: User;
  settings: Settings;

  constructor(private formBuilder: FormBuilder, private activatedRoute: ActivatedRoute,
              private userService: UserService, private snackBarService: SnackbarService) { }

  ngOnInit(): void {
    this.currentUser = this.activatedRoute.snapshot.data.currentUser;
    this.settings = this.currentUser.settings;

    this.mainForm = this.formBuilder.group({
      disabledNotifies: [this.settings.noticeSetting.disabledNotice, []],
      successDuration: [this.settings.noticeSetting.successDuration, []],
      errorDuration: [this.settings.noticeSetting.errorDuration, []],
      horizontalPosition: [this.settings.noticeSetting.horizontalPosition, []],
      verticalPosition: [this.settings.noticeSetting.verticalPosition, []],
    });
  }

  update() {
    if(this.mainForm.invalid)
      return;

    this.settings.noticeSetting.disabledNotice = this.mainForm.get('disabledNotifies').value;
    this.settings.noticeSetting.successDuration = this.mainForm.get('successDuration').value;
    this.settings.noticeSetting.errorDuration = this.mainForm.get('errorDuration').value;
    this.settings.noticeSetting.horizontalPosition = this.mainForm.get('horizontalPosition').value;
    this.settings.noticeSetting.verticalPosition = this.mainForm.get('verticalPosition').value;

    this.userService.setNoticeSetting(this.settings.noticeSetting).subscribe(() => {
      this.snackBarService.success('Настройки уведомлений изменены!', {
        duration: this.settings.noticeSetting.successDuration,
        horizontalPosition: this.settings.noticeSetting.horizontalPosition,
        verticalPosition: this.settings.noticeSetting.verticalPosition,
      });
    }, error => {
      console.log(error);
      this.snackBarService.error('Произошел сбой, попробуйте позже.', {
        duration: this.settings.noticeSetting.errorDuration,
        horizontalPosition: this.settings.noticeSetting.horizontalPosition,
        verticalPosition: this.settings.noticeSetting.verticalPosition,
      });
    });
  }

  formatLabel(value: number) {
    if (value >= 1000) {
      return (value / 1000) + 'с';
    }
    return value + 'мс';
  }

}
