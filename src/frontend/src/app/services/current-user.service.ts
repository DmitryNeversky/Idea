import {Injectable} from "@angular/core";
import {Settings} from "../models/Settings";
import {User} from "../models/User";
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Observable, of} from "rxjs";
import {tap} from "rxjs/operators";

@Injectable({
    providedIn: 'root'
})
export class CurrentUserService {

    private apiBaseUrl: string = environment.apiBaseUrl;

    private _currentUser: User;
    private _settings: Settings;

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

    get currentUser(): User {
        return this._currentUser;
    }

    set currentUser(value: User) {
        this._currentUser = value;
    }

    get settings(): Settings {
        return this._settings;
    }

    set settings(value: Settings) {
        this._settings = value;
    }
}