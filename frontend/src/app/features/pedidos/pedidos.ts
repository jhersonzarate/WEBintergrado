import { Component, inject, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PedidoService } from '../../core/services/pedido';
import { Pedido } from '../../shared/models/pedido';

@Component({
  selector: 'app-pedidos',
  imports: [CommonModule],
  templateUrl: './pedidos.html',
  styleUrl: './pedidos.scss'
})
export class Pedidos implements OnInit {
  private pedidoService = inject(PedidoService);
  pedidos = signal<Pedido[]>([]);
  loading = signal(true);
  expandedId = signal<number | null>(null);

  ngOnInit() {
    this.pedidoService.getMisPedidos().subscribe({
      next: p => { this.pedidos.set(p); this.loading.set(false); },
      error: () => this.loading.set(false)
    });
  }

  toggle(id: number) {
    this.expandedId.set(this.expandedId() === id ? null : id);
  }
}