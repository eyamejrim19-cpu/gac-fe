package com.bna.gac.dto;

import java.time.LocalDateTime;

public class ClientDTO {

    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String tel;
    private String cin;
    private String rne;
    private String adresse;
    private String typeClient;
    private Boolean active;
    private LocalDateTime dateCreation;

    // ================= GETTERS =================

    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getEmail() {
        return email;
    }

    public String getTel() {
        return tel;
    }

    public String getCin() {
        return cin;
    }

    public String getRne() {
        return rne;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getTypeClient() {
        return typeClient;
    }

    public Boolean getActive() {
        return active;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    // ================= SETTERS =================

    public void setId(Long id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public void setRne(String rne) {
        this.rne = rne;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setTypeClient(String typeClient) {
        this.typeClient = typeClient;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }
}
