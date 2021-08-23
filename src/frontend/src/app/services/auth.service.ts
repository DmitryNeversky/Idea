import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {User} from "../models/User";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  apiBaseUrl: string = environment.apiBaseUrl;

  isLoggedIn = false;

  redirectUrl: string | null = null;

  constructor(private httpClient: HttpClient) {}

  auth(user: User) {
    this.httpClient.post<boolean>(`${this.apiBaseUrl}/users/auth`, user).subscribe(r => {
      this.isLoggedIn = r;
    }, error => console.log(error));
  }

  registration(user: User) {
    this.httpClient.post<boolean>(`${this.apiBaseUrl}/users/registration`, user).subscribe(r => {
      this.isLoggedIn = r;
    }, error => console.log(error));
  }
}
