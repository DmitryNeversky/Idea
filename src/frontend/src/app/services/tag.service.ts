import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";

@Injectable({
    providedIn: 'root'
})
export class TagService {

    private apiBaseUrl: string = environment.apiBaseUrl;

    constructor(private httpClient: HttpClient) { }

    public getTags(): Observable<string[]> {
        return this.httpClient.get<string[]>(`${this.apiBaseUrl}/tags`);
    }

    public addTag(tags: string[]): Observable<string> {
        return this.httpClient.post<string>(`${this.apiBaseUrl}/tags/save`, tags);
    }

    public putTag(tag: string): Observable<string> {
        return this.httpClient.put<string>(`${this.apiBaseUrl}/tags/put/${tag}`, tag);
    }

    public deleteTag(tag: string): Observable<void> {
        return this.httpClient.delete<void>(`${this.apiBaseUrl}/tags/delete/${tag}`);
    }
}