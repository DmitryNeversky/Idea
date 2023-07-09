import {Injectable} from "@angular/core";
import { ActivatedRouteSnapshot, Router, RouterStateSnapshot } from "@angular/router";
import {AuthService} from "../services/auth.service";
import {Observable} from "rxjs";

@Injectable({
    providedIn: "root"
})
export class LoginGuard  {

    constructor(private router: Router, private authService: AuthService) {}

    canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
        if(this.authService.isAuthenticated()) {
            this.router.navigate(['']);
            return false;
        }
        return true;
    }
}