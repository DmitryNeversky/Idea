import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs';
import {Role} from "../models/Role";
import {RoleService} from "../services/role.service";

@Injectable({
    providedIn: 'root'
})
export class RolesResolver implements Resolve<Role[]> {

    constructor(private service: RoleService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Role[]> | Promise<Role[]>{

        return this.service.getRoles();
    }
}
