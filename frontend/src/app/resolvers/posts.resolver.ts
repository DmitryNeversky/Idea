import {Injectable} from "@angular/core";
import { ActivatedRouteSnapshot, RouterStateSnapshot } from "@angular/router";
import {Observable} from "rxjs";
import {PostService} from "../services/post.service";
import {Post} from "../models/Post";

@Injectable({
    providedIn: 'root'
})
export class PostsResolver  {

    constructor(private postService: PostService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Post[]> | Promise<Post[]>{

        return this.postService.getPosts();
    }
}
