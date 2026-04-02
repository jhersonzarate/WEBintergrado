import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Recomendacion } from '../../shared/models/pedido';

@Injectable({ providedIn: 'root' })
export class RecomendacionService {
  private apiUrl = `${environment.apiUrl}/recomendaciones`;

  constructor(private http: HttpClient) {}

  getRecomendaciones(): Observable<Recomendacion[]> {
    return this.http.get<Recomendacion[]>(this.apiUrl);
  }
}