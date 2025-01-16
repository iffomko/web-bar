import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { BarTable } from '../domain/bar-table';

@Injectable({
  providedIn: 'root'
})
export class BarTablesService {
  constructor(private http: HttpClient) {
  }

  loadAll(): Observable<BarTable[]> {
    return this.http.get<BarTable[]>('http://localhost:8080/api/v1/bar-tables');
  }
}
