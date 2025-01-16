import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpInterceptorFn, HttpHandlerFn } from '@angular/common/http';
import { AuthService } from '../services/auth.service';

@Injectable()
export class BasicAuthInterceptor implements HttpInterceptor {
  constructor(private authService: AuthService) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler) {
    const authHeader = this.authService.getAuth();
    const authReq = req.clone({
      setHeaders: {
        Authorization: authHeader
      }
    });
    return next.handle(authReq);
  }
}
