import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from "@angular/router";
import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {User} from "../models/User";
import {CurrentUserService} from "../services/current-user.service";

@Injectable({
    providedIn: "root"
})
export class AdminGuard implements CanActivate {

    constructor(private router: Router, private currentUserService: CurrentUserService) {}

    canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
        return new Observable<boolean>(obs => {
            this.currentUserService.getCurrentUser().subscribe((user: User) => {
                if(user.role == "ADMIN") {
                    obs.next(true);
                } else {
                    obs.next(false);
                    this.router.navigate(['']);
                }
            });
        });
    }
}