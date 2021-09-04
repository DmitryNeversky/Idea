import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Idea} from "../models/Idea";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class IdeaService {

  private apiBaseUrl: string = environment.apiBaseUrl;

  constructor(private httpClient: HttpClient) { }

  public getAll(): Observable<Idea[]> {
    return this.httpClient.get<Idea[]>(`${this.apiBaseUrl}/ideas`);
  }

  public getById(id: number|string): Observable<Idea> {
    return this.httpClient.get<Idea>(`${this.apiBaseUrl}/idea/${id}`);
  }

  public save(formData: FormData): Observable<Idea> {
    return this.httpClient.post<Idea>(`${this.apiBaseUrl}/idea/save`, formData);
  }

  public put(formData: FormData): Observable<Idea> {
    return this.httpClient.put<Idea>(`${this.apiBaseUrl}/idea/put`, formData);
  }

  public delete(id: number|string): Observable<void> {
    return this.httpClient.delete<void>(`${this.apiBaseUrl}/idea/delete/${id}`);
  }

  public addLook(id: number|string): Observable<void> {
    return this.httpClient.get<void>(`${this.apiBaseUrl}/idea/look/${id}`);
  }

  public addRating(id: number|string): void {
    this.httpClient.get<void>(`${this.apiBaseUrl}/idea/rating/add/${id}`)
        .subscribe(() => {}, error => console.log(error));
  }

  public reduceRating(id: number|string): void {
    this.httpClient.get<void>(`${this.apiBaseUrl}/idea/rating/reduce/${id}`)
        .subscribe(() => {}, error => console.log(error));
  }
}