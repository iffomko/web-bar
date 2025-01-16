import { Component } from '@angular/core';
import { HeaderComponent } from './components/header/header.component';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
  imports: [HeaderComponent, RouterOutlet],
  standalone: true
})
export class AppComponent {
  hasAuthenticated: boolean = false;

  handleLogin() {
    this.hasAuthenticated = true;
  }

  handleLogout(): void {
    this.hasAuthenticated = false;
  }
}
