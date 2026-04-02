import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ProductService } from '../../../core/services/product';
import { Categoria } from '../../../shared/models/product';

@Component({
  selector: 'app-product-form',
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './product-form.html',
  styleUrl: './product-form.scss'
})
export class ProductForm implements OnInit {
  private fb = inject(FormBuilder);
  private productService = inject(ProductService);
  private router = inject(Router);
  private route = inject(ActivatedRoute);

  categorias: Categoria[] = [];
  loading = false;
  isEdit = false;
  productId: number | null = null;

  form = this.fb.group({
    nombre: ['', [Validators.required]],
    descripcion: [''],
    precio: [0, [Validators.required, Validators.min(0.01)]],
    stock: [0, [Validators.min(0)]],
    imagenUrl: [''],
    marca: [''],
    categoriaId: [null as number | null, [Validators.required]],
    activo: [true]
  });

  ngOnInit() {
    this.productService.getCategorias().subscribe(c => this.categorias = c);
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEdit = true;
      this.productId = +id;
      this.productService.getById(this.productId).subscribe(p => {
        this.form.patchValue({ ...p, categoriaId: p.categoriaId });
      });
    }
  }

  submit() {
    if (this.form.invalid) return;
    this.loading = true;
    const data = this.form.value as any;

    const action = this.isEdit
      ? this.productService.update(this.productId!, data)
      : this.productService.create(data);

    action.subscribe({
      next: () => this.router.navigate(['/admin/dashboard']),
      error: () => this.loading = false
    });
  }
}