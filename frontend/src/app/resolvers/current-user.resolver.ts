import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs';
import {User} from "../models/User";
import {CurrentUserService} from "../services/current-user.service";

@Injectable({
    providedIn: 'root'
})
export class CurrentUserResolver implements Resolve<User> {

    constructor(private currentUserService: CurrentUserService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<User> | Promise<User> | User {
        return this.currentUserService.getCurrentUser();
    }
}
