import {Injectable} from '@angular/core';
import { ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import {Observable} from 'rxjs';
import {UserService} from "../services/user.service";
import {User} from "../models/User";

@Injectable({
    providedIn: 'root'
})
export class UsersResolver  {

    constructor(private userService: UserService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<User[]> | Promise<User[]> {
        return this.userService.getUsers();
    }
}
