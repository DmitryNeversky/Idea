import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {User} from "../models/User";
import {environment} from "../../environments/environment";
import {Observable} from "rxjs";
import {NoticeSetting} from "../models/settings/NoticeSetting";
import {Role} from "../models/Role";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private apiBaseUrl: string = environment.apiBaseUrl;

  constructor(private httpClient: HttpClient) { }

  public getUsers(): Observable<User[]> {
    return this.httpClient.get<User[]>(`${this.apiBaseUrl}/users`);
  }

  public getCurrentUser(): Observable<User> {
    return this.httpClient.get<User>(`${this.apiBaseUrl}/users/current`);
  }

  public getUserById(id: number|string): Observable<User> {
    return this.httpClient.get<User>(`${this.apiBaseUrl}/users/${id}`);
  }

  public putUser(formData: FormData): Observable<User> {
    return this.httpClient.put<User>(`${this.apiBaseUrl}/users`, formData);
  }

  public deleteUser(id: number|string): Observable<void> {
    return this.httpClient.delete<void>(`${this.apiBaseUrl}/${id}`);
  }

  public deleteNotification(id: number|string): Observable<void> {
    return this.httpClient.delete<void>(`${this.apiBaseUrl}/users/notification/${id}`);
  }

  public getEmailCode(): Observable<void> {
    return this.httpClient.get<void>(`${this.apiBaseUrl}/users/code`);
  }

  public verifyEmailCode(formData: FormData): Observable<void> {
    return this.httpClient.post<void>(`${this.apiBaseUrl}/users/code`, formData);
  }

  public changePassword(formData: FormData): Observable<void> {
    return this.httpClient.patch<void>(`${this.apiBaseUrl}/users/password/change`, formData);
  }

  public setNoticeSetting(noticeSetting: NoticeSetting): Observable<void> {
    return this.httpClient.put<void>(`${this.apiBaseUrl}/users/settings/notifies`, noticeSetting);
  }

  public blockUser(username: string): Observable<void> {
    return this.httpClient.patch<void>(`${this.apiBaseUrl}/users/${username}/block`, {});
  }

  public unblockUser(username: string): Observable<void> {
    return this.httpClient.patch<void>(`${this.apiBaseUrl}/users/${username}/unblock`, {});
  }

  public changeRoles(username: string, roles: Role[]): Observable<void> {
    return this.httpClient.patch<void>(`${this.apiBaseUrl}/users/${username}/roles`, roles);
  }
}
