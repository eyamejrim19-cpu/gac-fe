package com.bna.gac.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Facture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFacture;

    private String numero;
    private LocalDateTime dateEmission;
    private LocalDateTime datePaiement;
    private Double montant;
    private String statut;
    private String typeFacture;
    private String typePaiement;
    private String commentaireRejet;

    @ManyToOne
    @JoinColumn(name = "mission_id")
    private Mission mission;
}
