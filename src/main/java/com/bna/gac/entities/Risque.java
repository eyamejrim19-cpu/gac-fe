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
public class Risque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRisque;

    private Double montantPrincipal;
    private Double montantInteret;
    private Double montantTotal;
    private Integer periode;
    private LocalDateTime dateContrat;
    private LocalDateTime dateDeblocage;
    private LocalDateTime dateEcheance;
    private Double tauxInteret;

    @ManyToOne
    @JoinColumn(name = "dossier_id")
    private DossierContentieux dossier;

    @OneToMany(mappedBy = "risque", cascade = CascadeType.ALL)
    private Set<Garantie> garanties;

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

