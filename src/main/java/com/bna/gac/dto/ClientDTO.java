package com.bna.gac.dto;

import java.time.LocalDateTime;


public class ClientDTO {

    private Long id;
    private String nom;
    private String prenom;
    private String email;

    /** Maps to DB column: tel — displayed in UI column: Téléphone */
    private String tel;

    /**
     * Maps to DB column: cin — displayed in UI column: CIN.
     * Outbound: masked for PHYSIQUE (e.g. "******78"), null for MORALE.
     * Inbound:  raw 8-digit value.
     */
    private String cin;

    /**
     * Maps to DB column: rne — displayed in UI column: RNE.
     * Outbound: masked for MORALE (e.g. "*****67"), null for PHYSIQUE.
     * Inbound:  raw 7-digit value.
     */
    private String rne;

    private String adresse;
    private String typeClient;

    /** Source of truth for client status. Never null in responses. */
    private Boolean active;

    /**
     * Derived from {@code active}. Set by the mapper on every outbound response.
     * Values: "Actif" | "Inactif"
     * Displayed in UI column: Statut.
     * Read-only — ignored on inbound requests.
     */
    private String statut;

    private LocalDateTime dateCreation;

    // ================= GETTERS =================

    public Long getId()                  { return id; }
    public String getNom()               { return nom; }
    public String getPrenom()            { return prenom; }
    public String getEmail()             { return email; }
    public String getTel()               { return tel; }
    public String getCin()               { return cin; }
    public String getRne()               { return rne; }
    public String getAdresse()           { return adresse; }
    public String getTypeClient()        { return typeClient; }
    public Boolean getActive()           { return active; }
    public String getStatut()            { return statut; }
    public LocalDateTime getDateCreation() { return dateCreation; }

    // ================= SETTERS =================

    public void setId(Long id)                          { this.id = id; }
    public void setNom(String nom)                      { this.nom = nom; }
    public void setPrenom(String prenom)                { this.prenom = prenom; }
    public void setEmail(String email)                  { this.email = email; }
    public void setTel(String tel)                      { this.tel = tel; }
    public void setCin(String cin)                      { this.cin = cin; }
    public void setRne(String rne)                      { this.rne = rne; }
    public void setAdresse(String adresse)              { this.adresse = adresse; }
    public void setTypeClient(String typeClient)        { this.typeClient = typeClient; }
    public void setActive(Boolean active)               { this.active = active; }
    public void setStatut(String statut)                { this.statut = statut; }
    public void setDateCreation(LocalDateTime dateCreation) { this.dateCreation = dateCreation; }
}
