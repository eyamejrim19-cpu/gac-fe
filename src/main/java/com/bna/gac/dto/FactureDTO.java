package com.bna.gac.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class FactureDTO {

    private Long idFacture;
    private Long id;
    private String numero;
    private LocalDate dateEmission;
    private Double montant;
    private String statut;
    private String typeFacture;

    private Long missionId;
}

