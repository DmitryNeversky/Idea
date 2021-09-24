import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";
import {Tag} from "../models/Tag";

@Injectable({
    providedIn: 'root'
})
export class TagService {

    private apiBaseUrl: string = environment.apiBaseUrl;

    constructor(private httpClient: HttpClient) { }

    public getTags(): Observable<Tag[]> {
        return this.httpClient.get<Tag[]>(`${this.apiBaseUrl}/tags`);
    }

    public getTagById(id: number|string): Observable<Tag> {
        return this.httpClient.get<Tag>(`${this.apiBaseUrl}/tags/id/${id}`);
    }

    public getTagByName(name: string): Observable<Tag> {
        return this.httpClient.get<Tag>(`${this.apiBaseUrl}/tags/name/${name}`);
    }

    public saveTag(tag: Tag): Observable<Tag> {
        return this.httpClient.post<Tag>(`${this.apiBaseUrl}/tags`, tag);
    }

    public putTag(tag: Tag): Observable<Tag> {
        return this.httpClient.put<Tag>(`${this.apiBaseUrl}/tags`, tag);
    }

    public deleteTag(id: number|string): Observable<void> {
        return this.httpClient.delete<void>(`${this.apiBaseUrl}/tags/${id}`);
    }
}