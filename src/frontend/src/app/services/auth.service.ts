import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Observable} from "rxjs";
import {tap} from "rxjs/operators";
import {Router} from "@angular/router";
import {JwtHelperService} from "@auth0/angular-jwt";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiBaseUrl: string = environment.apiBaseUrl;
  private jwt = new JwtHelperService();

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

  logout(): void {
    localStorage.removeItem('access_token');
    localStorage.removeItem('refresh_token');
    this.router.navigate(['/auth']);
  }

  isAuthenticated(): boolean {
      return !!localStorage.getItem('access_token')
          && !this.jwt.isTokenExpired(localStorage.getItem('access_token'))
          && !this.jwt.isTokenExpired(localStorage.getItem('refresh_token'));
  }

  refreshToken(): Observable<any> {
      return this.httpClient.get<any>(`${this.apiBaseUrl}/token/refresh`, {
          headers: {
              'Authorization': `Bearer ${localStorage.getItem('refresh_token')}`
          }
      }).pipe(
          tap(response => {
              localStorage.setItem('access_token', response['access_token']);
              localStorage.setItem('refresh_token', response['refresh_token']);
          })
      );
  }
}
