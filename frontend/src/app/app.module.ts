import { NgModule } from "@angular/core";
import { BrowserModule } from "@angular/platform-browser";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { HTTP_INTERCEPTORS, HttpClientModule } from "@angular/common/http";
import { CommonModule } from "@angular/common";

import { AppRoutingModule } from "./app-routing.module";
import { AppComponent } from "./app.component";
import { LoginComponent } from "./auth/login/login.component";
import { JwtInterceptor } from "./auth/jwt.interceptor";
import { NavbarComponent } from "./layout/navbar/navbar.component";
import { ClientListComponent } from "./clients/client-list/client-list.component";
import { ClientFormComponent } from "./clients/client-form/client-form.component";
import { PrestataireListComponent } from "./prestataires/prestataire-list/prestataire-list.component";

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    NavbarComponent,
    ClientListComponent,
    ClientFormComponent,
    PrestataireListComponent
  ],
  imports: [
    BrowserModule,
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    AppRoutingModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {}
