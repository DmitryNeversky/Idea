import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Idea} from "../models/Idea";
import {environment} from "../../environments/environment";
import {Status} from "../models/Status";

@Injectable({
  providedIn: 'root'
})
export class IdeaService {

  private apiBaseUrl: string = environment.apiBaseUrl;

  constructor(private httpClient: HttpClient) { }

  public getIdeas(): Observable<Idea[]> {
    return this.httpClient.get<Idea[]>(`${this.apiBaseUrl}/ideas`);
  }

  public getIdeaById(id: number|string): Observable<Idea> {
    return this.httpClient.get<Idea>(`${this.apiBaseUrl}/ideas/${id}`);
  }

  public saveIdea(formData: FormData): Observable<Idea> {
    return this.httpClient.post<Idea>(`${this.apiBaseUrl}/ideas`, formData);
  }

  public putIdea(formData: FormData): Observable<Idea> {
    return this.httpClient.put<Idea>(`${this.apiBaseUrl}/ideas`, formData);
  }

  public deleteIdea(id: number|string): Observable<void> {
    return this.httpClient.delete<void>(`${this.apiBaseUrl}/ideas/${id}`);
  }

  public addLook(id: number|string): Observable<void> {
    return this.httpClient.patch<void>(`${this.apiBaseUrl}/ideas/${id}/look`, {});
  }

  public addRating(id: number|string): void {
    this.httpClient.patch<void>(`${this.apiBaseUrl}/ideas/${id}/rating/add`, {})
        .subscribe(() => {}, error => console.log(error));
  }

  public reduceRating(id: number|string): void {
    this.httpClient.patch<void>(`${this.apiBaseUrl}/ideas/${id}/rating/reduce`, {})
        .subscribe(() => {}, error => console.log(error));
  }

  public changeStatus(id: string|number, status: Status): Observable<void> {
    return this.httpClient.patch<void>(`${this.apiBaseUrl}/ideas/${id}/status`, status);
  }
}