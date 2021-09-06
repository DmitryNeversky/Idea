import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs';
import {UserService} from "../services/user.service";
import {User} from "../models/User";
import {tap} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class UserResolver implements Resolve<User> {

  constructor(private userService: UserService,
              private router: Router) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<User> | Promise<User> {
    return this.userService.getUserById(route.parent.paramMap.get('id')).pipe(
        tap((user: User) => {
          if(!user)
            this.router.navigate(['/']);
        })
    );
  }
}
