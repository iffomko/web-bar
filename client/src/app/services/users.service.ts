import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from '../domain/user';

@Injectable({
  providedIn: 'root'
})
export class UsersService {
  constructor(private http: HttpClient) {
  }

  register(user: User) {
    return this.http.post<User>('http://localhost:8080/api/v1/users/registration', user)
  }

  login(user: User) {
    return this.http.post<User>('http://localhost:8080/api/v1/users/login', user)
  }
}
