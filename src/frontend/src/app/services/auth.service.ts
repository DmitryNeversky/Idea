import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {User} from "../models/User";
import {Observable} from "rxjs";
import {tap} from "rxjs/operators";
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiBaseUrl: string = environment.apiBaseUrl;

  constructor(private httpClient: HttpClient, private router: Router) {}

  login(formData: FormData): Observable<any> {
    return this.httpClient.post<any>(`${this.apiBaseUrl}/login`, formData)
        .pipe(
          tap(response => {
            localStorage.setItem('access_token', response['access_token']);
            localStorage.setItem('refresh_token', response['refresh_token']);
          })
        );
  }

  registration(user: User): Observable<User> {
    return this.httpClient.post<User>(`${this.apiBaseUrl}/user/save`, user);
  }

  logout(): void {
    localStorage.removeItem('access_token');
    localStorage.removeItem('refresh_token');
    this.router.navigate(['/auth']);
  }
}
