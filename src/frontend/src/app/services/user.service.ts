import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {User} from "../models/User";
import {environment} from "../../environments/environment";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private apiBaseUrl: string = environment.apiBaseUrl;

  constructor(private httpClient: HttpClient) { }

  public getCurrentUser(): Observable<User> {
    return this.httpClient.get<User>(`${this.apiBaseUrl}/user/current`);
  }

  public getById(id: number|string): Observable<User> {
    return this.httpClient.get<User>(`${this.apiBaseUrl}/user/${id}`);
  }

  public putUser(formData: FormData): Observable<User> {
    return this.httpClient.put<User>(`${this.apiBaseUrl}/user/put`, formData);
  }

  public deleteUser(id: number|string): Observable<void> {
    return this.httpClient.delete<void>(`${this.apiBaseUrl}/user/delete/${id}`);
  }

  public deleteNotification(id: number|string): Observable<void> {
    return this.httpClient.delete<void>(`${this.apiBaseUrl}/notification/delete/${id}`);
  }
}
