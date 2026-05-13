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
public class Mission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMission;

    private String typeMission;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    private String statut;
    private String resultat;
    private String commentaire;
    private String commentaireRejet;

    @ManyToOne
    @JoinColumn(name = "dossier_id")
    private DossierContentieux dossier;

    @ManyToOne
    @JoinColumn(name = "prestataire_id")
    private Prestataire prestataire;

    @OneToMany(mappedBy = "mission")
    private Set<Facture> factures;
}
