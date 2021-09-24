import {Injectable} from "@angular/core";
import {Settings} from "../models/Settings";
import {User} from "../models/User";
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Observable, of} from "rxjs";
import {map, tap} from "rxjs/operators";

@Injectable({
    providedIn: 'root'
})
export class CurrentUserService {

    private apiBaseUrl: string = environment.apiBaseUrl;

    public static currentUser: User;

    constructor(private httpClient: HttpClient) {}

    public getCurrentUser(): Observable<User> {
        if(CurrentUserService.currentUser)
            return of(CurrentUserService.currentUser);

        return this.httpClient.get<User>(`${this.apiBaseUrl}/users/current`).pipe(
            tap((user: User) => {
                CurrentUserService.currentUser = user;
            }, (error: HttpErrorResponse) => {
                console.log(error.error);
            })
        );
    }

    public getSettings(): Observable<Settings> {
        return this.getCurrentUser().pipe(
            map(user => user.settings)
        );
    }
}