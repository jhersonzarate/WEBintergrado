import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth-guard';
import { adminGuard } from './core/guards/admin-guard';

export const routes: Routes = [
  { path: '', redirectTo: '/productos', pathMatch: 'full' },
  {
    path: 'auth',
    children: [
      {
        path: 'login',
        loadComponent: () => import('./features/auth/login/login').then(m => m.Login)
      },
      {
        path: 'register',
        loadComponent: () => import('./features/auth/register/register').then(m => m.Register)
      }
    ]
  },
  {
    path: 'productos',
    loadComponent: () => import('./features/productos/product-list/product-list').then(m => m.ProductList)
  },
  {
    path: 'productos/:id',
    loadComponent: () => import('./features/productos/product-detail/product-detail').then(m => m.ProductDetail)
  },
  {
    path: 'carrito',
    canActivate: [authGuard],
    loadComponent: () => import('./features/carrito/carrito').then(m => m.Carrito)
  },
  {
    path: 'pedidos',
    canActivate: [authGuard],
    loadComponent: () => import('./features/pedidos/pedidos').then(m => m.Pedidos)
  },
  {
    path: 'recomendaciones',
    canActivate: [authGuard],
    loadComponent: () => import('./features/recomendaciones/recomendaciones').then(m => m.Recomendaciones)
  },
  {
    path: 'admin',
    canActivate: [authGuard, adminGuard],
    children: [
      {
        path: 'dashboard',
        loadComponent: () => import('./features/admin/dashboard/dashboard').then(m => m.Dashboard)
      },
      {
        path: 'productos/nuevo',
        loadComponent: () => import('./features/admin/product-form/product-form').then(m => m.ProductForm)
      },
      {
        path: 'productos/:id/editar',
        loadComponent: () => import('./features/admin/product-form/product-form').then(m => m.ProductForm)
      }
    ]
  },
  { path: '**', redirectTo: '/productos' }
];