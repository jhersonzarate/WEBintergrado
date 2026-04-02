import { Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Cart, CartItemRequest } from '../../shared/models/cart';

@Injectable({ providedIn: 'root' })
export class CartService {
  private apiUrl = `${environment.apiUrl}/carrito`;
  cart = signal<Cart | null>(null);

  constructor(private http: HttpClient) {}

  getCart(): Observable<Cart> {
    return this.http.get<Cart>(this.apiUrl).pipe(
      tap(c => this.cart.set(c))
    );
  }

  addItem(request: CartItemRequest): Observable<Cart> {
    return this.http.post<Cart>(`${this.apiUrl}/items`, request).pipe(
      tap(c => this.cart.set(c))
    );
  }

  updateItem(itemId: number, cantidad: number): Observable<Cart> {
    return this.http.put<Cart>(`${this.apiUrl}/items/${itemId}`, null, {
      params: { cantidad }
    }).pipe(tap(c => this.cart.set(c)));
  }

  clearCart(): Observable<void> {
    return this.http.delete<void>(this.apiUrl).pipe(
      tap(() => this.cart.set(null))
    );
  }

  itemCount(): number {
    return this.cart()?.items.reduce((acc, i) => acc + i.cantidad, 0) ?? 0;
  }
}