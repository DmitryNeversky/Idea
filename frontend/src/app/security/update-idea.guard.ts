import {Injectable} from "@angular/core";
import { ActivatedRouteSnapshot, Router, RouterStateSnapshot } from "@angular/router";
import {Observable} from "rxjs";
import {User} from "../models/User";
import {CurrentUserService} from "../services/current-user.service";

@Injectable({
    providedIn: "root"
})
export class UpdateIdeaGuard  {

    constructor(private router: Router, private currentUserService: CurrentUserService) {}

    canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
        let id = next.params.id;
        return new Observable<boolean>(obs => {
            this.currentUserService.getCurrentUser().subscribe((user: User) => {
                obs.next(!!user.ideas.find(i => i.id == id) || user.role == "ADMIN");
            });
        });
    }
}