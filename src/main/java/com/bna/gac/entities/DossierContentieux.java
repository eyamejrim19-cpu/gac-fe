package com.bna.gac.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DossierContentieux {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDossier;

    private String reference;
    private LocalDateTime dateOuverture;
    private String statut;
    private String niveauRisque;
    private LocalDateTime dateCloture;
    private Double montantInitial;
    private Double montantRecupere;

    @ManyToOne
    @JoinColumn(name = "charge_dossier_id")
    private ChargeDossier chargeDossier;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(mappedBy = "dossier", cascade = CascadeType.ALL)
    private Set<Affaire> affaires;

    @OneToMany(mappedBy = "dossier", cascade = CascadeType.ALL)
    private Set<Risque> risques;
}