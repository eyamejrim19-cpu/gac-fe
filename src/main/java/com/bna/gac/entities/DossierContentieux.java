package com.bna.gac.entities;

import com.bna.gac.entities.enums.DossierStatus;
import jakarta.persistence.*;
import lombok.*;

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

    @Enumerated(EnumType.STRING)
    private DossierStatus statut;

    private String niveauRisque;
    private LocalDateTime dateCloture;
    private Double montantInitial;
    private Double montantRecupere;
    private String commentaireRejet;

    @ManyToOne
    @JoinColumn(name = "charge_dossier_id")
    private ChargeDossier chargeDossier;

    @ManyToOne
    @JoinColumn(name = "id_client")
    private Client client;

    @OneToMany(mappedBy = "dossier", cascade = CascadeType.ALL)
    private Set<Affaire> affaires;

    @OneToMany(mappedBy = "dossier", cascade = CascadeType.ALL)
    private Set<Risque> risques;

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

