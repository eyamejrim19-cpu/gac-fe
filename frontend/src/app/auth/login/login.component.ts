import { Component } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { Router } from "@angular/router";
import { AuthService } from "../auth.service";
@Component({ selector: "app-login", templateUrl: "./login.component.html" })
export class LoginComponent {
  form: FormGroup;
  loading = false;
  errorMessage = "";
  constructor(private fb: FormBuilder, private auth: AuthService, private router: Router) {
    this.form = this.fb.group({ username: ["", Validators.required], password: ["", Validators.required] });
    if (this.auth.isLoggedIn()) this.router.navigate(["/clients"]);
  }
  onSubmit(): void {
    if (this.form.invalid) { this.form.markAllAsTouched(); return; }
    this.loading = true; this.errorMessage = "";
    this.auth.login(this.form.value).subscribe({
      next: () => { this.loading = false; this.router.navigate(["/clients"]); },
      error: (err) => {
        this.loading = false;
        if (err.status === 401 || err.status === 403) this.errorMessage = "Identifiants incorrects.";
        else if (err.status === 0) this.errorMessage = "Serveur inaccessible. Vérifiez que le backend est démarré.";
        else this.errorMessage = err?.error?.message || "Une erreur est survenue.";
      }
    });
  }
}
