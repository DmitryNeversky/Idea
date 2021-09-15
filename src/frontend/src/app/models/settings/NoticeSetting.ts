export class NoticeSetting {
    disabledNotice: boolean = false;
    successDuration: number = 2000;
    errorDuration: number = 3000;
    horizontalPosition: 'start' | 'center' | 'end' = 'start';
    verticalPosition: 'top' | 'bottom' = 'bottom';
}