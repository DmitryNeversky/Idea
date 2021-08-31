import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Observable} from "rxjs";
import {Injectable} from "@angular/core";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        if(!!localStorage.getItem('access_token') && !req.headers.has('Authorization')) {
            const clone = req.clone({
                headers: req.headers.append('Authorization',
                    `Bearer ${localStorage.getItem('access_token')}`)
            });
            return next.handle(clone);
        }
        return next.handle(req);
    }
}