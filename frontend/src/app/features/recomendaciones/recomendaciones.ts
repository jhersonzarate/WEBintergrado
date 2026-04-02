import { Component, inject, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { RecomendacionService } from '../../core/services/recomendacion';
import { CartService } from '../../core/services/cart';
import { Recomendacion } from '../../shared/models/pedido';

@Component({
  selector: 'app-recomendaciones',
  imports: [CommonModule, RouterLink],
  templateUrl: './recomendaciones.html',
  styleUrl: './recomendaciones.scss'
})
export class Recomendaciones implements OnInit {
  private recomService = inject(RecomendacionService);
  cartService = inject(CartService);

  recomendaciones = signal<Recomendacion[]>([]);
  loading = signal(true);
  addingId = signal<number | null>(null);

  ngOnInit() {
    this.recomService.getRecomendaciones().subscribe({
      next: r => { this.recomendaciones.set(r); this.loading.set(false); },
      error: () => this.loading.set(false)
    });
  }

  addToCart(r: Recomendacion) {
    this.addingId.set(r.productoId);
    this.cartService.addItem({ productoId: r.productoId, cantidad: 1 }).subscribe({
      next: () => this.addingId.set(null),
      error: () => this.addingId.set(null)
    });
  }
}