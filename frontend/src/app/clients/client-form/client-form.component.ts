import { Component, OnInit } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { ActivatedRoute, Router } from "@angular/router";
import { ClientService } from "../client.service";
import { TypeClient } from "../client.model";
@Component({ selector: "app-client-form", templateUrl: "./client-form.component.html" })
export class ClientFormComponent implements OnInit {
  form!: FormGroup; isEditMode=false; clientId?: number; loading=false; errorMessage="";
  readonly typeOptions = [{ label:"Personne Physique", value:"PHYSIQUE" as TypeClient },{ label:"Personne Morale", value:"MORALE" as TypeClient }];
  constructor(private fb: FormBuilder, private svc: ClientService, private route: ActivatedRoute, private router: Router) {}
  ngOnInit(): void {
    this.form = this.fb.group({ typeClient:["PHYSIQUE",Validators.required], nom:["",Validators.required], prenom:[""], email:["", [Validators.required,Validators.email]], tel:["", [Validators.required,Validators.pattern(/^[24579][0-9]{7}$/)]], adresse:[""], cin:["", [Validators.required,Validators.pattern(/^[0-9]{8}$/)]], rne:[""] });
    const id = this.route.snapshot.paramMap.get("id");
    if (id) { this.isEditMode=true; this.clientId=+id; this.svc.getById(+id).subscribe({ next: c => { this.form.patchValue(c); this.applyValidators(c.typeClient); }, error: () => this.errorMessage="Impossible de charger." }); }
    this.form.get("typeClient")!.valueChanges.subscribe((t: TypeClient) => this.applyValidators(t));
  }
  applyValidators(type: TypeClient): void {
    const cin=this.form.get("cin")!; const rne=this.form.get("rne")!;
    if (type==="PHYSIQUE") { cin.setValidators([Validators.required,Validators.pattern(/^[0-9]{8}$/)]); rne.setValidators([]); rne.setValue(""); }
    else { rne.setValidators([Validators.required,Validators.pattern(/^[0-9]{7}$/)]); cin.setValidators([]); cin.setValue(""); }
    cin.updateValueAndValidity(); rne.updateValueAndValidity();
  }
  get isPhysique() { return this.form.get("typeClient")?.value==="PHYSIQUE"; }
  get isMorale()   { return this.form.get("typeClient")?.value==="MORALE"; }
  onSubmit(): void {
    if (this.form.invalid) { this.form.markAllAsTouched(); return; }
    this.loading=true; this.errorMessage="";
    const raw=this.form.value;
    const payload = { typeClient:raw.typeClient as TypeClient, nom:raw.nom, prenom:raw.prenom||undefined, email:raw.email, tel:raw.tel, adresse:raw.adresse||undefined, ...(raw.typeClient==="PHYSIQUE" ? {cin:raw.cin} : {rne:raw.rne}) };
    const req$ = this.isEditMode ? this.svc.update(this.clientId!,payload) : this.svc.create(payload);
    req$.subscribe({ next: () => { this.loading=false; this.router.navigate(["/clients"]); }, error: (err) => { this.loading=false; this.errorMessage=err?.error?.message||"Erreur."; } });
  }
  onCancel(): void { this.router.navigate(["/clients"]); }
}
