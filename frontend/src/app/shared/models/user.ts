export interface User {
  id?: number;
  nombre: string;
  email: string;
  rol: 'USER' | 'ADMIN';
  preferencia?: 'DULCE' | 'SALADO' | 'MIXTO';
}

export interface AuthResponse {
  token: string;
  email: string;
  nombre: string;
  rol: string;
}

export interface LoginRequest {
  email: string;
  password: string;
}

export interface RegisterRequest {
  nombre: string;
  email: string;
  password: string;
  preferencia?: string;
}