import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from "@angular/router";
import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {AuthService} from "./services/auth.service";
import {JwtHelperService} from "@auth0/angular-jwt";

@Injectable({
    providedIn: "root"
})
export class AuthGuard implements CanActivate {

    private jwt = new JwtHelperService();

    constructor(private router: Router, private authService: AuthService) {}

    canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
        if(this.authService.isAuthenticated()) {
            return true;
        } else if(!this.jwt.isTokenExpired(localStorage.getItem('refresh_token'))) {
            if(this.jwt.isTokenExpired(localStorage.getItem('access_token'))) {
                this.authService.refreshToken().subscribe(response => {
                    this.router.navigate([state.url]);
                    return !!response['access_token'];
                }, error => console.log(error));
            } else {
                return true
            }
        } else {
            this.router.navigate(['/auth'])
            return false;
        }

        return false;
    }
}