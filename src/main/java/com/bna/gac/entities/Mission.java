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

    @ManyToOne
    @JoinColumn(name = "affaire_id")
    private Affaire affaire;

    @OneToMany(mappedBy = "mission")
    private Set<Facture> factures;
}