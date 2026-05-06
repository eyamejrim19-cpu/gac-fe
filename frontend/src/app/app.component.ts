import { Component } from "@angular/core";
import { AuthService } from "./auth/auth.service";
@Component({
  selector: "app-root",
  template: `<app-navbar *ngIf="auth.isLoggedIn()"></app-navbar><router-outlet></router-outlet>`
})
export class AppComponent { constructor(public auth: AuthService) {} }
