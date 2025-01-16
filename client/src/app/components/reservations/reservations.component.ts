import { Component, OnInit } from '@angular/core';
import { NgForOf, NgIf } from '@angular/common';
import { ReservationService } from '../../services/reservation.service';
import { AuthService } from '../../services/auth.service';
import { BarTable } from '../../domain/bar-table';
import { Capacity } from '../../domain/capacity';
import { BarTablesService } from '../../services/bar-tables.service';
import { zip } from 'rxjs';
import moment from 'moment';

@Component({
  selector: 'app-reservations',
  templateUrl: './reservations.component.html',
  styleUrls: ['./reservations.component.css'],
  imports: [
    NgForOf,
    NgIf
  ]
})
export class ReservationsComponent implements OnInit {
  tablesReserved: RowData[] = [];

  constructor(private reservationService: ReservationService,
              private barTablesService: BarTablesService,
              private authService: AuthService) {
  }

  ngOnInit(): void {
    this.loadAll();
  }

  private loadAll() {
    const user = this.authService.currentUser()!;
    zip(this.reservationService.loadAll(user.phone), this.barTablesService.loadAll())
      .subscribe({
        next: ([reservations, barTables]) => {
          const tablesIdByReservationId = new Map(reservations.map((reservation) => [reservation.tableId, reservation]));
          this.tablesReserved = barTables
            .filter(table => tablesIdByReservationId.has(table.id))
            .map(table => {
              let capacityNumber;
              switch (table.capacity) {
                case Capacity.double:
                  capacityNumber = 2;
                  break;
                case Capacity.four_seater:
                  capacityNumber = 4;
                  break;
                case Capacity.eight_seater:
                  capacityNumber = 8;
                  break;
              }
              const reservation = tablesIdByReservationId.get(table.id)!;
              return <RowData>{
                name: `Стол: ${table.number}`,
                capacity: capacityNumber,
                reservationId: reservation.id,
                reservationDate: reservation.reservationDate,
                table: table
              }
            })
        },
        error: error => {
          if (error.error.message) {
            return;
          }
          alert(error.error.message);
        }
      })
  }

  cancel(row: RowData) {
    this.reservationService.cancel(row.reservationId)
      .subscribe(() => this.loadAll());
  }

  formatDate(date: Date) {
    const formattedDate = moment(date).format('DD.MM.YYYY HH:mm');
    return formattedDate;
  }
}

interface RowData {
  name: string;
  capacity: number;
  reservationId: number;
  reservationDate: Date;
  table: BarTable;
}
