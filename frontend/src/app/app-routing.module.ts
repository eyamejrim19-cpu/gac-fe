import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { LoginComponent } from "./auth/login/login.component";
import { ClientListComponent } from "./clients/client-list/client-list.component";
import { ClientFormComponent } from "./clients/client-form/client-form.component";
import { PrestataireListComponent } from "./prestataires/prestataire-list/prestataire-list.component";
import { AuthGuard } from "./auth/auth.guard";

const routes: Routes = [
  { path: "login", component: LoginComponent },
  { path: "clients",          component: ClientListComponent,    canActivate: [AuthGuard] },
  { path: "clients/new",      component: ClientFormComponent,    canActivate: [AuthGuard] },
  { path: "clients/edit/:id", component: ClientFormComponent,    canActivate: [AuthGuard] },
  { path: "prestataires",     component: PrestataireListComponent, canActivate: [AuthGuard] },
  { path: "", redirectTo: "/clients", pathMatch: "full" },
  { path: "**", redirectTo: "/clients" }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
