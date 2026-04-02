import { Component, inject, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { ProductService } from '../../../core/services/product';
import { Product } from '../../../shared/models/product';

@Component({
  selector: 'app-dashboard',
  imports: [CommonModule, RouterLink],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.scss'
})
export class Dashboard implements OnInit {
  private productService = inject(ProductService);
  productos = signal<Product[]>([]);
  loading = signal(true);

  ngOnInit() {
    this.productService.getAll().subscribe({
      next: p => { this.productos.set(p); this.loading.set(false); },
      error: () => this.loading.set(false)
    });
  }

  eliminar(id: number) {
    if (!confirm('¿Eliminar producto?')) return;
    this.productService.delete(id).subscribe(() => {
      this.productos.update(list => list.filter(p => p.id !== id));
    });
  }
}