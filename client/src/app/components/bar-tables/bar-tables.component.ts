import { Component, OnInit } from '@angular/core';
import { BarTablesService } from '../../services/bar-tables.service';
import { NgForOf, NgIf } from '@angular/common';
import { Capacity } from '../../domain/capacity';
import { Reservation } from '../../domain/reservation';
import { AuthService } from '../../services/auth.service';
import { ReservationService } from '../../services/reservation.service';

@Component({
  selector: 'app-bar-tables',
  templateUrl: './bar-tables.component.html',
  imports: [
    NgForOf,
    NgIf
  ],
  styleUrls: ['./bar-tables.component.css']
})
export class BarTablesComponent implements OnInit {
  tables: TableData[] = [];

  constructor(private barTablesService: BarTablesService, private authService: AuthService,
              private reservationService: ReservationService) {
  }

  ngOnInit(): void {
    this.barTablesService.loadAll()
      .subscribe(barTables => {
        this.tables = barTables.map(table => {
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
          return <TableData>{
            id: table.id,
            name: `Стол: ${table.number}`,
            capacity: capacityNumber
          }
        })
      });
  }

  order(event: any, table: TableData) {
    event.preventDefault();
    const dateTime : string | undefined = event.srcElement.children[0].value;
    if (!dateTime) {
      return;
    }
    const user = this.authService.currentUser();
    if (!user) {
      alert('Авторизируйтесь в системе');
    }
    const reservation = <Reservation>{
      id: 0,
      customerId: 0,
      tableId: table.id,
      reservationDate: new Date(dateTime)
    }
    this.reservationService.make(user!.phone, reservation)
      .subscribe({
        next: () => {
          alert(`Вы забронировали стол №${reservation.tableId}`)
        },
        error: err => {
          if (!err.error.message) {
            console.log(err);
          }
          alert(err.error.message);
        }
      })
  }
}

export interface TableData {
  id: number;
  name: string;
  capacity: number;
}
