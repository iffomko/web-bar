import {Injectable} from '@angular/core';
import {User} from '../domain/user';

const CURRENT_USER_KEY = 'current-user';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  getAuth() {
    const user = this.loadCurrentUser();
    if (!user) {
      return '';
    }
    console.log(user);
    const credits = btoa(`${user.phone}:${user.password}`)
    return `Basic ${credits}`;
  }

  logout() {
    localStorage.removeItem(CURRENT_USER_KEY);
  }

  login(user: User) {
    localStorage.setItem(CURRENT_USER_KEY, JSON.stringify(user));
  }

  currentUser() {
    return this.loadCurrentUser();
  }

  private loadCurrentUser() {
    const rowCurrentUser = localStorage.getItem(CURRENT_USER_KEY);
    return rowCurrentUser ? <User>JSON.parse(rowCurrentUser) : null;
  }
}
