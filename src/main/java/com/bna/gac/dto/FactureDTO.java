package com.bna.gac.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class FactureDTO {

    private Long idFacture;
    private Long id;
    private String numero;
    private LocalDate dateEmission;
    private LocalDate dateEcheance;
    private LocalDateTime datePaiement;
    private Double montant;
    private String statut;
    private String typeFacture;
    private String typePaiement;

    private Long missionId;
    private Long dossierId;
    private String commentaireRejet;
}
