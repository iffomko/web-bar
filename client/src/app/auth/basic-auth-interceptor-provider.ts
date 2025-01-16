import { Provider } from '@angular/core';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { BasicAuthInterceptor } from './auth-interceptor';

export const basicAuthInterceptorProvider: Provider = { provide: HTTP_INTERCEPTORS,
  useClass: BasicAuthInterceptor, multi: true };
