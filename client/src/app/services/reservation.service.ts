import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Reservation } from '../domain/reservation';

@Injectable({
  providedIn: 'root',
})
export class ReservationService {
  constructor(private http: HttpClient) {
  }

  loadAll(userPhone: string) {
    return this.http.get<Reservation[]>('http://localhost:8080/api/v1/reservations', {
      params: {
        userPhone: userPhone
      }
    })
  }

  make(userPhone: string, reservation: Reservation) {
    return this.http.post('http://localhost:8080/api/v1/reservations', reservation, {
      params: {
        userPhone: userPhone
      }
    })
  }

  cancel(reservationId: number) {
    return this.http.delete('http://localhost:8080/api/v1/reservations', {
      params: {
        id: reservationId,
      }
    })
  }
}
