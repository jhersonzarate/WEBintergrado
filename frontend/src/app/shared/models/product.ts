export interface Product {
  id: number;
  nombre: string;
  descripcion: string;
  precio: number;
  stock: number;
  imagenUrl: string;
  marca: string;
  categoriaNombre: string;
  categoriaTipo: string;
  categoriaId: number;
  activo: boolean;
}

export interface ProductRequest {
  nombre: string;
  descripcion: string;
  precio: number;
  stock: number;
  imagenUrl: string;
  marca: string;
  categoriaId: number;
  activo: boolean;
}

export interface Categoria {
  id: number;
  nombre: string;
  tipo: string;
}