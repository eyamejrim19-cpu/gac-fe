export type TypeClient = "PHYSIQUE" | "MORALE";
export interface Client {
  id?: number; nom: string; prenom?: string; email: string; tel: string;
  adresse?: string; typeClient: TypeClient; cin?: string; rne?: string;
  active?: boolean; dateCreation?: string;
}
export interface PagedResponse<T> { content: T[]; totalElements: number; totalPages: number; number: number; size: number; }
export function buildClientPayload(raw: Partial<Client> & { typeClient: TypeClient }): Partial<Client> {
  const base: Partial<Client> = { nom: raw.nom, prenom: raw.prenom||undefined, email: raw.email, tel: raw.tel, adresse: raw.adresse||undefined, typeClient: raw.typeClient, active: raw.active };
  if (raw.typeClient === "PHYSIQUE") base.cin = raw.cin; else base.rne = raw.rne;
  return base;
}
