import { Injectable } from "@angular/core";
import { HttpClient, HttpParams } from "@angular/common/http";
import { Observable } from "rxjs";
import { Client, buildClientPayload, TypeClient, PagedResponse } from "./client.model";
import { environment } from "../../environments/environment";
@Injectable({ providedIn: "root" })
export class ClientService {
  private readonly baseUrl = `${environment.apiUrl}/clients`;
  constructor(private http: HttpClient) {}
  getAll(page=0,size=10,search?: string,type?: TypeClient|"",active?: boolean): Observable<PagedResponse<Client>> {
    let params = new HttpParams().set("page",page.toString()).set("size",size.toString());
    if (search) params = params.set("search",search);
    if (type)   params = params.set("type",type);
    if (active !== undefined) params = params.set("active",active.toString());
    return this.http.get<PagedResponse<Client>>(this.baseUrl,{params});
  }
  getById(id: number): Observable<Client> { return this.http.get<Client>(`${this.baseUrl}/${id}`); }
  create(client: Partial<Client> & { typeClient: TypeClient }): Observable<Client> { return this.http.post<Client>(this.baseUrl, buildClientPayload(client)); }
  update(id: number, client: Partial<Client> & { typeClient: TypeClient }): Observable<Client> { return this.http.put<Client>(`${this.baseUrl}/${id}`, buildClientPayload(client)); }
  delete(id: number): Observable<void> { return this.http.delete<void>(`${this.baseUrl}/${id}`); }
  deactivate(id: number): Observable<Client> { return this.http.patch<Client>(`${this.baseUrl}/${id}/deactivate`,{}); }
  reactivate(id: number): Observable<Client> { return this.http.patch<Client>(`${this.baseUrl}/${id}/reactivate`,{}); }
}
