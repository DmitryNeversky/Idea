import {Injectable, NgZone} from "@angular/core";
import {MatSnackBar, MatSnackBarConfig} from "@angular/material/snack-bar";
import {CurrentUserService} from "../../services/current-user.service";
import {Settings} from "../../models/Settings";
import {NoticeSetting} from "../../models/settings/NoticeSetting";

@Injectable({
    providedIn: "root",
})
export class SnackbarService {

    private readonly config: MatSnackBarConfig;
    public settings: NoticeSetting;

    constructor(private snackbar: MatSnackBar, private zone: NgZone, private service: CurrentUserService) {
        this.config = new MatSnackBarConfig();
        this.config.panelClass = ["snackbar-container"];
        service.getSettings().subscribe((settings: Settings) => {
            this.settings = settings.noticeSetting;
            this.config.horizontalPosition = settings.noticeSetting.horizontalPosition;
            this.config.verticalPosition = settings.noticeSetting.verticalPosition;
        });
    }

    success(message: string, config?: MatSnackBarConfig) {
        this.config.panelClass = ["snackbar-container", "success"];
        this.config.duration = this.settings.successDuration;
        this.show(message, config);
    }

    error(message: string = 'Произошел сбой, попробуйте позже.', config?: MatSnackBarConfig) {
        this.config.panelClass = ["snackbar-container", "error"];
        this.config.duration = this.settings.errorDuration;
        this.show(message, config);
    }

    warning(message: string) {
        this.config.panelClass = ["snackbar-container", "warning"];
        this.show(message);
    }

    private show(message: string, config?: MatSnackBarConfig) {
        if(this.settings.disabledNotice)
            return;
        this.config.horizontalPosition = this.settings.horizontalPosition;
        this.config.verticalPosition = this.settings.verticalPosition;
        config = config || this.config;
        this.zone.run(() => {
            this.snackbar.open(message, null, config);
        });
    }
}