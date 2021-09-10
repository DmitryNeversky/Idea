import {Injectable} from "@angular/core";
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from "@angular/router";
import {AuthService} from "../services/auth.service";
import {Observable, Subject} from "rxjs";
import {User} from "../models/User";
import {tap} from "rxjs/operators";
import {UserService} from "../services/user.service";

@Injectable({
    providedIn: "root"
})
export class UpdateIdeaGuard implements CanActivate {

    constructor(private router: Router, private authService: AuthService,
                private userService: UserService) {}

    canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
        let id = next.params.id;
        let subject = new Subject<boolean>();
        this.userService.getCurrentUser().subscribe((user: User) => {
            subject.next(user.ideas.find(i => i.id == id) != null);
        });

        return subject.asObservable().pipe(
            tap((next: boolean) => {
                if(!next) {
                    this.router.navigate(['']);
                }
            })
        );
    }
}