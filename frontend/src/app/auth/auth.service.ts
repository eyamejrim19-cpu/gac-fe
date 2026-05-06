import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Router } from "@angular/router";
import { Observable, tap } from "rxjs";
import { environment } from "../../environments/environment";

export interface LoginRequest { username: string; password: string; }
export interface UserResponse { id: number; username: string; email: string; }
export interface AuthResponse { token: string; user: UserResponse; }

const TOKEN_KEY = "gac_token";
const USER_KEY  = "gac_user";

@Injectable({ providedIn: "root" })
export class AuthService {
  private readonly loginUrl = `${environment.apiUrl}/auth/login`;
  constructor(private http: HttpClient, private router: Router) {}

  login(credentials: LoginRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(this.loginUrl, credentials).pipe(
      tap(res => {
        localStorage.setItem(TOKEN_KEY, res.token);
        localStorage.setItem(USER_KEY, JSON.stringify(res.user));
      })
    );
  }

  logout(): void {
    localStorage.removeItem(TOKEN_KEY);
    localStorage.removeItem(USER_KEY);
    this.router.navigate(["/login"]);
  }

  getToken(): string | null { return localStorage.getItem(TOKEN_KEY); }

  getUser(): UserResponse | null {
    const raw = localStorage.getItem(USER_KEY);
    return raw ? JSON.parse(raw) : null;
  }

  isLoggedIn(): boolean {
    const token = this.getToken();
    if (!token) return false;
    try {
      const payload = JSON.parse(atob(token.split(".")[1]));
      return payload.exp * 1000 > Date.now();
    } catch { return false; }
  }
}
