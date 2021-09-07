import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from "@angular/router";
import {Injectable} from "@angular/core";
import {Observable, Subject} from "rxjs";
import {UserService} from "../services/user.service";
import {User} from "../models/User";
import {tap} from "rxjs/operators";

@Injectable({
    providedIn: "root"
})
export class AdminGuard implements CanActivate {

    constructor(private router: Router, private userService: UserService) {}

    canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
        let subject = new Subject<boolean>();
        this.userService.getCurrentUser().subscribe((user: User) => {
            subject.next(user != null);
        });

        return subject.asObservable().pipe(
            tap((next: boolean) => {
                if(!next) {
                    this.router.navigate(['']);
                }
            })
        );
    }
}