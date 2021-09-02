import {Injectable} from "@angular/core";
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from "@angular/router";
import {Observable} from "rxjs";
import {PostService} from "../../services/post.service";
import {Post} from "../../models/Post";

@Injectable({
    providedIn: 'root'
})
export class PostsResolver implements Resolve<Post[]> {

    constructor(private postService: PostService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Post[]> | Promise<Post[]>{

        return this.postService.getAll();
    }
}
