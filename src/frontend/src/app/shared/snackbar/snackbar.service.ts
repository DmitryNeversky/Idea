import {Injectable, NgZone} from "@angular/core";
import {MatSnackBar, MatSnackBarConfig} from "@angular/material/snack-bar";
import {CurrentUserService} from "../../services/current-user.service";
import {User} from "../../models/User";

@Injectable({
    providedIn: "root",
})
export class SnackbarService {

    private readonly config: MatSnackBarConfig;
    private currentUser: User;

    constructor(private snackbar: MatSnackBar, private zone: NgZone, private service: CurrentUserService) {
        this.config = new MatSnackBarConfig();
        this.config.panelClass = ["snackbar-container"];
        service.getCurrentUser().subscribe((user: User) => {
            this.currentUser = user;
            this.config.horizontalPosition = this.currentUser.settings.noticeSetting.horizontalPosition;
            this.config.verticalPosition = this.currentUser.settings.noticeSetting.verticalPosition;
        });
    }

    success(message: string, config?: MatSnackBarConfig) {
        this.config.panelClass = ["snackbar-container", "success"];
        this.config.duration = this.currentUser.settings.noticeSetting.successDuration;
        this.show(message, config);
    }

    error(message: string, config?: MatSnackBarConfig) {
        this.config.panelClass = ["snackbar-container", "error"];
        this.config.duration = this.currentUser.settings.noticeSetting.errorDuration;
        this.show(message, config);
    }

    warning(message: string) {
        this.config.panelClass = ["snackbar-container", "warning"];
        this.show(message);
    }

    private show(message: string, config?: MatSnackBarConfig) {
        if(this.currentUser.settings.noticeSetting.disabledNotice)
            return;
        config = config || this.config;
        this.zone.run(() => {
            this.snackbar.open(message, null, config);
        });
    }
}