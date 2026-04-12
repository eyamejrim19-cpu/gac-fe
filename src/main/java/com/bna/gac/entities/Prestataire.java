package com.bna.gac.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
public class Prestataire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPrestataire;

    private String nom;
    private String prenom;
    private String telephone;
    private String email;
    private String adresse;
    private String specialite;

    private Double tarifJournalier;

    private boolean actif;

    @Enumerated(EnumType.STRING)
    private TypePrestataire typePrestataire;

    public boolean getActif() {

        return false;
    }
}