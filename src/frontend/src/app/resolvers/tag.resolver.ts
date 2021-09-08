import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs';
import {TagService} from "../services/tag.service";
import {Tag} from "../models/Tag";

@Injectable({
    providedIn: 'root'
})
export class TagResolver implements Resolve<Tag[]> {

    constructor(private tagService: TagService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Tag[]> | Promise<Tag[]>{

        return this.tagService.getTags();
    }
}
