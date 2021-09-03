import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs';
import {TagService} from "../services/tag.service";

@Injectable({
    providedIn: 'root'
})
export class TagResolver implements Resolve<string[]> {

    constructor(private tagService: TagService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<string[]> | Promise<string[]>{

        return this.tagService.getAll();
    }
}
