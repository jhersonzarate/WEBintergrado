export interface PedidoDetalle {
  productoId: number;
  productoNombre: string;
  cantidad: number;
  precioUnitario: number;
  subtotal: number;
}

export interface Pedido {
  id: number;
  total: number;
  estado: string;
  creadoEn: string;
  detalles: PedidoDetalle[];
}

export interface Recomendacion {
  productoId: number;
  nombre: string;
  imagenUrl: string;
  precio: number;
  motivo: string;
  categoriaNombre: string;
}