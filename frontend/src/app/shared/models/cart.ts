export interface CartItem {
  id: number;
  productoId: number;
  productoNombre: string;
  imagenUrl: string;
  precioUnitario: number;
  cantidad: number;
  subtotal: number;
}

export interface Cart {
  id: number;
  items: CartItem[];
  total: number;
}

export interface CartItemRequest {
  productoId: number;
  cantidad: number;
}