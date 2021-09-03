import {Injectable} from "@angular/core";
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from "@angular/router";
import {AuthService} from "../services/auth.service";
import {Observable} from "rxjs";

@Injectable({
    providedIn: "root"
})
export class PathGuard implements CanActivate {

    constructor(private router: Router, private authService: AuthService) {}

    canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
        if(state.url.toString().includes("auth") || state.url.toString().includes("registration") && this.authService.isAuthenticated()) {
            this.router.navigate(['']);
            return false;
        }
        return true;
    }
}