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

  public get(id: number): Observable<Idea> {
    return this.httpClient.get<Idea>(`${this.apiBaseUrl}/ideas/${id}`);
  }

  public add(formData: FormData): Observable<Idea> {
    return this.httpClient.post<Idea>(`${this.apiBaseUrl}/ideas/add`, formData);
  }

  public update(formData: FormData): Observable<Idea> {
    return this.httpClient.put<Idea>(`${this.apiBaseUrl}/ideas/put/${formData.get('id')}`, formData);
  }

  public delete(id: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.apiBaseUrl}/ideas/delete/${id}`);
  }
}