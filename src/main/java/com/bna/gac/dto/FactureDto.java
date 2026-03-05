package com.bna.gac.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class FactureDto {

    private Long idFacture;
    private String numero;
    private LocalDate dateEmission;
    private Double montant;
    private String statut;
    private String typeFacture;

    private Long missionId;
    private Long prestataireId;
}
