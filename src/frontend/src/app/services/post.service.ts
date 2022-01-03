import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {Post} from "../models/Post";

@Injectable({
  providedIn: 'root'
})
export class PostService {

  private apiBaseUrl: string = environment.apiBaseUrl;

  constructor(private httpClient: HttpClient) { }

  public getPosts(): Observable<Post[]> {
    return this.httpClient.get<Post[]>(`${this.apiBaseUrl}/posts`);
  }

  public getPostById(id: number|string): Observable<Post> {
    return this.httpClient.get<Post>(`${this.apiBaseUrl}/posts/${id}`);
  }

  public savePost(post: Post): Observable<Post> {
    return this.httpClient.post<Post>(`${this.apiBaseUrl}/posts`, post);
  }

  public putPost(post: Post): Observable<Post> {
    return this.httpClient.put<Post>(`${this.apiBaseUrl}/posts/${post.id}`, post);
  }

  public deletePost(id: number|string): Observable<void> {
    return this.httpClient.delete<void>(`${this.apiBaseUrl}/posts/${id}`);
  }
}
