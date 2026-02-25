package com.bna.gac.entities;


import jakarta.persistence.*;
import lombok.*;
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
    @OneToMany(mappedBy = "risque")
    private Set<Garantie> garanties;
}
