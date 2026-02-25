package com.bna.gac.entities;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Garantie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGarantie;

    private String typeGarantie;
    private String description;
    private Double valeur;
    private String statut;

    @ManyToOne
    @JoinColumn(name = "risque_id")
    private Risque risque;
}
