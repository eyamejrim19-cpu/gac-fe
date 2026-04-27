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
public class Affaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAffaire;

    private String numeroProcedure;
    private LocalDateTime dateDebut;
    private String statut;
    private String tribunal;
    private String jugement;

    @ManyToOne
    @JoinColumn(name = "dossier_id")
    private DossierContentieux dossier;

    @OneToMany(mappedBy = "affaire")
    private Set<Mission> missions;
    @OneToMany(mappedBy = "affaire")
    private Set<Audience> audiences;
}
