import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { CartService } from '../../core/services/cart';
import { PedidoService } from '../../core/services/pedido';

@Component({
  selector: 'app-carrito',
  imports: [CommonModule, RouterLink],
  templateUrl: './carrito.html',
  styleUrl: './carrito.scss'
})
export class Carrito implements OnInit {
  cartService = inject(CartService);
  private pedidoService = inject(PedidoService);
  private router = inject(Router);

  loading = false;
  checkingOut = false;

  ngOnInit() {
    this.loading = true;
    this.cartService.getCart().subscribe(() => this.loading = false);
  }

  updateItem(itemId: number, cantidad: number) {
    this.cartService.updateItem(itemId, cantidad).subscribe();
  }

  checkout() {
    this.checkingOut = true;
    this.pedidoService.checkout().subscribe({
      next: () => this.router.navigate(['/pedidos']),
      error: () => this.checkingOut = false
    });
  }
}