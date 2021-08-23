import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from "@angular/router";
import {Injectable} from "@angular/core";
import {AuthService} from "./services/auth.service";

@Injectable({
    providedIn: "root"
})
export class AuthGuard implements CanActivate {

    constructor(private authService: AuthService, private router: Router) {}

    canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): true | UrlTree {
        const url: string = state.url;

        return this.checkLogin(url);
    }

    checkLogin(url: string): true | UrlTree {
        if (this.authService.isLoggedIn) { return true; }
        this.authService.redirectUrl = url;

        return this.router.parseUrl('/auth');
    }
}