package com.bna.gac.entities;

import com.bna.gac.entities.enums.AffaireStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
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
    private LocalDate dateDebut;

    @Enumerated(EnumType.STRING)
    private AffaireStatus statut;

    private String tribunal;
    private String jugement;

    @ManyToOne
    @JoinColumn(name = "dossier_id")
    private DossierContentieux dossier;

    @ManyToOne
    @JoinColumn(name = "prestataire_id")
    private Prestataire prestataire;

    @OneToMany(mappedBy = "affaire")
    private Set<Mission> missions;

    @OneToMany(mappedBy = "affaire")
    private Set<Audience> audiences;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}