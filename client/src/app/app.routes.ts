import { Routes } from '@angular/router';
import { BarTablesComponent } from './components/bar-tables/bar-tables.component';
import { HomeComponent } from './components/home/home.component';
import { ReservationsComponent } from './components/reservations/reservations.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'tables', component: BarTablesComponent },
  { path: 'reservations', component: ReservationsComponent }
];
