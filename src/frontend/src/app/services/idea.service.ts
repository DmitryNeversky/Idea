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

  public get(id: string): Observable<Idea> {
    return this.httpClient.get<Idea>(`${this.apiBaseUrl}/idea/${id}`);
  }

  public add(formData: FormData): Observable<Idea> {
    return this.httpClient.post<Idea>(`${this.apiBaseUrl}/idea/save`, formData);
  }

  public put(idea: Idea, formData: FormData): Observable<Idea> {
    formData.append('idea', JSON.stringify(idea));
    return this.httpClient.put<Idea>(`${this.apiBaseUrl}/idea/put`, formData);
  }

  public delete(id: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.apiBaseUrl}/idea/delete/${id}`);
  }

  public addLook(formData: FormData): Observable<void> {
    return this.httpClient.post<void>(`${this.apiBaseUrl}/idea/look`, formData);
  }

  public addRating(formData: FormData) {
    this.httpClient.post(`${this.apiBaseUrl}/idea/rating/add`, formData).subscribe();
  }

  public reduceRating(formData: FormData) {
    this.httpClient.post(`${this.apiBaseUrl}/idea/rating/reduce`, formData).subscribe();
  }
}