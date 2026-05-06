import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { ClientService } from "../client.service";
import { Client, TypeClient } from "../client.model";
@Component({ selector: "app-client-list", templateUrl: "./client-list.component.html" })
export class ClientListComponent implements OnInit {
  clients: Client[]=[]; loading=false; errorMessage="";
  currentPage=0; pageSize=10; totalElements=0; totalPages=0;
  searchTerm=""; filterType: TypeClient|""=""; filterActive?: boolean;
  constructor(private svc: ClientService, private router: Router) {}
  ngOnInit(): void { this.load(); }
  load(): void {
    this.loading=true; this.errorMessage="";
    this.svc.getAll(this.currentPage,this.pageSize,this.searchTerm,this.filterType,this.filterActive).subscribe({
      next: r => { this.clients=r.content; this.totalElements=r.totalElements; this.totalPages=r.totalPages; this.loading=false; },
      error: () => { this.errorMessage="Impossible de charger."; this.loading=false; }
    });
  }
  onSearch(): void { this.currentPage=0; this.load(); }
  onPageChange(p: number): void { this.currentPage=p; this.load(); }
  onCreate(): void { this.router.navigate(["/clients/new"]); }
  onEdit(id: number): void { this.router.navigate([`/clients/edit/${id}`]); }
  onDelete(id: number): void {
    if (!confirm("Supprimer ?")) return;
    this.svc.delete(id).subscribe({ next: () => this.load(), error: () => alert("Erreur.") });
  }
  onToggleActive(c: Client): void {
    const req$ = c.active ? this.svc.deactivate(c.id!) : this.svc.reactivate(c.id!);
    req$.subscribe({ next: () => this.load(), error: () => alert("Erreur statut.") });
  }
}
