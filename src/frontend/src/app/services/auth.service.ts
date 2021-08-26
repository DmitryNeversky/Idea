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

  apiBaseUrl: string = environment.apiBaseUrl;

  constructor(private httpClient: HttpClient,
              private router: Router) {}

  login(formData: FormData): Observable<any> {
    return this.httpClient.post<any>(`${this.apiBaseUrl}/api/login`, formData)
        .pipe(
          tap(x => {
            localStorage.setItem('access_token', x['access_token']);
            localStorage.setItem('refresh_token', x['refresh_token']);
          })
        );
  }

  registration(user: User): Observable<User> {
    return this.httpClient.post<User>(`${this.apiBaseUrl}/api/registration`, user);
  }

  logout(): void {
    localStorage.removeItem('access_token');
    localStorage.removeItem('refresh_token');
    this.router.navigate(['/auth']);
  }
}
