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

    public getAll(): Observable<string[]> {
        return this.httpClient.get<string[]>(`${this.apiBaseUrl}/tags`);
    }

    public add(tags: string[]): Observable<string> {
        return this.httpClient.post<string>(`${this.apiBaseUrl}/tags/add`, tags);
    }

    public put(tag: string): Observable<string> {
        return this.httpClient.put<string>(`${this.apiBaseUrl}/tags/put/${tag}`, tag);
    }

    public delete(tag: string): Observable<void> {
        return this.httpClient.delete<void>(`${this.apiBaseUrl}/tags/delete/${tag}`);
    }
}