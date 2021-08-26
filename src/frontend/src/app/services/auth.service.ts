import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {User} from "../models/User";
import {Observable} from "rxjs";
import {tap} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  apiBaseUrl: string = environment.apiBaseUrl;

  public static token: string;

  constructor(private httpClient: HttpClient) {}

  login(formData: FormData): Observable<any> {
    return this.httpClient.post<any>(`${this.apiBaseUrl}/api/login`, formData)
        .pipe(
          tap(x => { AuthService.token = x['access_token']; })
        );
  }

  registration(user: User): Observable<User> {
    return this.httpClient.post<User>(`${this.apiBaseUrl}/api/registration`, user);
  }

  isAuthenticated(): boolean {
    return !!AuthService.token;
  }
}
