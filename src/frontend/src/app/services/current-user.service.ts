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

    public currentUser: User;

    constructor(private httpClient: HttpClient) {}

    public getCurrentUser(): Observable<User> {
        if(this.currentUser)
            return of(this.currentUser);

        return this.httpClient.get<User>(`${this.apiBaseUrl}/user/current`).pipe(
            tap((user: User) => {
                this.currentUser = user;
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