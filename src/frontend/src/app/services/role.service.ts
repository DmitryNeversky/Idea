import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {Role} from "../models/Role";

@Injectable({
    providedIn: 'root'
})
export class RoleService {

    private apiBaseUrl: string = environment.apiBaseUrl;

    constructor(private httpClient: HttpClient) { }

    public getRoles(): Observable<Role[]> {
        return this.httpClient.get<Role[]>(`${this.apiBaseUrl}/roles`);
    }

    public saveRole(role: Role): Observable<Role> {
        return this.httpClient.post<Role>(`${this.apiBaseUrl}/role/save`, role);
    }

    public putRole(role: Role): Observable<Role> {
        return this.httpClient.put<Role>(`${this.apiBaseUrl}/role/put`, role);
    }

    public deleteRole(id: number|string): Observable<void> {
        return this.httpClient.delete<void>(`${this.apiBaseUrl}/role/delete/${id}`);
    }
}
