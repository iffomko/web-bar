import {Role} from './role';

export interface User {
  phone: string;
  password: string;
  role: Role,
  firstName: string,
  lastName: string
}
