import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from "@angular/router";
import {Injectable} from "@angular/core";
import {AuthService} from "./services/auth.service";
import {Observable} from "rxjs";

@Injectable({
    providedIn: "root"
})
export class AuthGuard implements CanActivate {

    constructor(private authService: AuthService, private router: Router) {}

    canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
        if(this.authService.isAuthenticated()) {
            console.log("Here is true")
            return true;
        } else {
            console.log("Here is not true")
            this.router.navigate(['/auth'])
            return false;
        }
    }
}