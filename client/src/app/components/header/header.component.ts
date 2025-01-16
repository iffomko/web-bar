import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { ModalComponent } from '../modal/modal.component';
import { NgIf } from '@angular/common';
import { User } from '../../domain/user';
import { Role } from '../../domain/role';
import { UsersService } from '../../services/users.service';
import { AuthService } from '../../services/auth.service';
import { Router, RouterLink } from '@angular/router';

const PHONE_REGEXP = /^(\+7|8)\d{10}$/;

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
  imports: [
    ModalComponent,
    NgIf,
    RouterLink
  ]
})
export class HeaderComponent implements OnInit {
  showLoginModal = false;
  showRegisterModal = false;
  userRegistration = <User>{
    firstName: '',
    lastName: '',
    phone: '',
    password: ''
  }
  userLogin = <User>{
    phone: '',
    password: ''
  }
  registrationErrorMessage?: string;
  loginErrorMessage?: string;
  currentUser?: User;

  @Output()
  loginEvent = new EventEmitter<void>();
  @Output()
  logoutEvent = new EventEmitter<void>();

  constructor(private usersService: UsersService, private authService: AuthService, private router: Router) {
  }

  ngOnInit(): void {
    const user = this.authService.currentUser();
    if (!user) {
      return;
    }
    this.currentUser = user;
  }

  openLoginModal() {
    this.showLoginModal = true;
    this.userLogin = this.createDefaultAccount();
    this.loginErrorMessage = '';
  }

  openRegisterModal() {
    this.showLoginModal = false;
    this.showRegisterModal = true;
    this.userRegistration = this.createDefaultAccount();
    this.registrationErrorMessage = '';
  }

  closeModals() {
    this.showLoginModal = false;
    this.showRegisterModal = false;
  }

  login(event: any) {
    event.preventDefault();
    if (!this.validateLoginAccount()) {
      return;
    }
    this.loginErrorMessage = '';
    this.usersService.login(this.userLogin).subscribe({
      next: user => this.processLogin(user, this.userLogin),
      error: req => {
        this.registrationErrorMessage = req.error.message
      }
    })
  }

  register(event: any) {
    event.preventDefault();
    if (!this.validateRegistrationAccount()) {
      return;
    }
    this.registrationErrorMessage = '';
    this.userRegistration.role = Role.admin;
    this.usersService.register(this.userRegistration).subscribe({
      next: user => this.processLogin(user, this.userRegistration),
      error: req => this.registrationErrorMessage = req.error.message
    });
  }

  private processLogin(authUser: User, beforeAuthUser: User) {
    this.loginEvent.emit();
    this.currentUser = <User>{
      firstName: authUser.firstName,
      lastName: authUser.lastName,
      phone: authUser.phone,
      password: beforeAuthUser.password
    };
    this.authService.login(this.currentUser);
    this.closeModals();
  }

  logout(event: any) {
    event.preventDefault();
    this.authService.logout();
    this.currentUser = undefined;
    this.logoutEvent.emit();
    this.router.navigate(['/']);
  }

  changeLoginPhone(event: any) {
    this.userLogin.phone = event.target.value;
  }

  changeLoginPassword(event: any) {
    this.userLogin.password = event.target.value;
  }

  changeRegisterFirstName(event: any) {
    this.userRegistration.firstName = event.target.value;
  }

  changeRegisterLastName(event: any) {
    this.userRegistration.lastName = event.target.value;
  }

  changeRegisterPhone(event: any) {
    this.userRegistration.phone = event.target.value;
  }

  changeRegisterPassword(event: any) {
    this.userRegistration.password = event.target.value;
  }

  private createDefaultAccount() {
    return <User>{
      firstName: '',
      lastName: '',
      phone: '',
      password: ''
    };
  }

  private validateRegistrationAccount() {
    if (this.isInvalidFirstName(this.userRegistration.firstName)) {
      this.registrationErrorMessage = 'Пропущено имя';
      return false;
    }
    if (this.isInvalidLastName(this.userRegistration.lastName)) {
      this.registrationErrorMessage = 'Пропущена фамилия';
      return false;
    }
    if (this.isInvalidPhone(this.userRegistration.phone)) {
      this.registrationErrorMessage = 'Пропущен мобильный телефон';
      return false;
    }
    if (this.isInvalidFormatPhone(this.userRegistration.phone)) {
      this.registrationErrorMessage = 'Невалидный формат номера телефона';
      return false;
    }
    if (this.isInvalidPassword(this.userRegistration.password)) {
      this.registrationErrorMessage = 'Слишком простой пароль';
      return false;
    }
    return true;
  }

  private validateLoginAccount() {
    if (this.isInvalidPhone(this.userLogin.phone)) {
      this.loginErrorMessage = 'Пропущен мобильный телефон';
      return false;
    }
    if (this.isInvalidFormatPhone(this.userLogin.phone)) {
      this.loginErrorMessage = 'Невалидный формат номера телефона';
      return false;
    }
    if (this.isInvalidPassword(this.userLogin.password)) {
      this.loginErrorMessage = 'Пропущен пароль';
      return false;
    }
    return true;
  }

  private isInvalidFirstName(firstName?: string): boolean {
    return !firstName || firstName.length === 0;
  }

  private isInvalidLastName(lastName?: string): boolean {
    return !lastName || lastName.length === 0;
  }

  private isInvalidPhone(phone?: string): boolean {
    return !phone || phone.length === 0;
  }

  private isInvalidFormatPhone(phone: string): boolean {
    return !PHONE_REGEXP.test(phone);
  }

  private isInvalidPassword(password?: string): boolean {
    return !password || password.length === 0;
  }
}

