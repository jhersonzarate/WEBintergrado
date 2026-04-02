import { Component } from '@angular/core';

@Component({
  selector: 'app-footer',
  template: `
    <footer class="footer">
      <div class="container">
        <p>🍿 Snacktopia &copy; 2026 — Sistema de recomendación de snacks</p>
      </div>
    </footer>
  `,
  styles: [`
    .footer {
      background: #1e1b4b;
      color: #a5b4fc;
      padding: 1.25rem 0;
      text-align: center;
      font-size: 0.875rem;
      margin-top: 4rem;
    }
  `]
})
export class Footer {}