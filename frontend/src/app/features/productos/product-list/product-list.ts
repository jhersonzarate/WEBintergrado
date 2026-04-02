import { Component, inject, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { ProductService } from '../../../core/services/product';
import { CartService } from '../../../core/services/cart';
import { AuthService } from '../../../core/services/auth';
import { Product, Categoria } from '../../../shared/models/product';

@Component({
  selector: 'app-product-list',
  imports: [CommonModule, RouterLink, FormsModule],
  templateUrl: './product-list.html',
  styleUrl: './product-list.scss'
})
export class ProductList implements OnInit {
  private productService = inject(ProductService);
  private cartService = inject(CartService);
  auth = inject(AuthService);

  products = signal<Product[]>([]);
  categorias = signal<Categoria[]>([]);
  loading = signal(true);
  addingId = signal<number | null>(null);

  selectedCategoria = 0;
  searchMarca = '';

  ngOnInit() {
    this.loadCategorias();
    this.loadProducts();
  }

  loadProducts() {
    this.loading.set(true);
    this.productService.getAll(
      this.selectedCategoria || undefined,
      this.searchMarca || undefined
    ).subscribe({
      next: p => { this.products.set(p); this.loading.set(false); },
      error: () => this.loading.set(false)
    });
  }

  loadCategorias() {
    this.productService.getCategorias().subscribe(c => this.categorias.set(c));
  }

  filterByCategoria(id: number) {
    this.selectedCategoria = id;
    this.loadProducts();
  }

  addToCart(product: Product) {
    if (!this.auth.isLoggedIn()) return;
    this.addingId.set(product.id);
    this.cartService.addItem({ productoId: product.id, cantidad: 1 }).subscribe({
      next: () => this.addingId.set(null),
      error: () => this.addingId.set(null)
    });
  }
}