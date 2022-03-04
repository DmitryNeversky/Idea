import {Injectable, NgZone} from "@angular/core";
import {MatSnackBar, MatSnackBarConfig} from "@angular/material/snack-bar";

@Injectable({
    providedIn: "root",
})
export class SnackbarService {

    private readonly config: MatSnackBarConfig;

    constructor(private snackbar: MatSnackBar, private zone: NgZone) {
        this.config = new MatSnackBarConfig();
        this.config.panelClass = ["snackbar-container"];
        if(!localStorage.getItem("disabledNotice")) {
            localStorage.setItem('disabledNotice', '0');
            localStorage.setItem('successDuration', '2000');
            localStorage.setItem('errorDuration', '3000');
            localStorage.setItem('horizontalPosition', 'start');
            localStorage.setItem('verticalPosition', 'bottom');
        }
    }

    success(message: string, config?: MatSnackBarConfig) {
        this.config.panelClass = ["snackbar-container", "success"];
        this.config.duration = +localStorage.getItem('successDuration');
        this.show(message, config);
    }

    error(message: string = 'Произошел сбой, попробуйте позже.', config?: MatSnackBarConfig) {
        this.config.panelClass = ["snackbar-container", "error"];
        this.config.duration = +localStorage.getItem('errorDuration');
        this.show(message, config);
    }

    warning(message: string) {
        this.config.panelClass = ["snackbar-container", "warning"];
        this.show(message);
    }

    private show(message: string, config?: MatSnackBarConfig) {
        if(!!+localStorage.getItem("disabledNotice"))
            return;
        // @ts-ignore
        this.config.horizontalPosition = localStorage.getItem("horizontalPosition");
        // @ts-ignore
        this.config.verticalPosition = localStorage.getItem("verticalPosition");
        config = config || this.config;
        this.zone.run(() => {
            this.snackbar.open(message, null, config);
        });
    }
}