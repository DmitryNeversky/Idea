import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs';
import {UserService} from "../services/user.service";
import {User} from "../models/User";

@Injectable({
    providedIn: 'root'
})
export class CurrentUserResolver implements Resolve<User> {

    constructor(private userService: UserService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<User> | Promise<User> {
        return this.userService.getCurrentUser();
    }
}
