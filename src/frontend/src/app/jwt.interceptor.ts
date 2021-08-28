import {Injectable} from "@angular/core";
import {
    HttpErrorResponse,
    HttpEvent,
    HttpHandler,
    HttpInterceptor,
    HttpRequest,
    HttpResponse
} from "@angular/common/http";
import {Observable} from "rxjs";
import {JwtHelperService} from "@auth0/angular-jwt";
import {tap} from "rxjs/operators";

@Injectable()
export class JwtInterceptor implements HttpInterceptor {

    constructor(public jwtHelper: JwtHelperService) {}

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return next.handle(request).pipe(
            tap((event: HttpEvent<any>) => {
                if (event instanceof HttpResponse) {
                    // do stuff with response if you want
                }
        }),
            tap((err: any) => {
                if (err instanceof HttpErrorResponse) {
                    if (err.status === 403) {
                        // redirect to the login route
                        // or show a modal
                    }
                }
        }));
    }
}