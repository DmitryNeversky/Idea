import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Observable} from "rxjs";
import {AuthService} from "./services/auth.service";

export class AuthInterceptor implements HttpInterceptor{

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        if(AuthService.token != null) {
            const clone = req.clone({
                headers: req.headers.append('Authorization', `Bearer ${AuthService.token}`)
            });
            return next.handle(clone);
        }
        return next.handle(req);
    }
}