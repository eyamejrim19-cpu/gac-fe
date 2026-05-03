package com.bna.gac.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DossierContentieuxDTO {

    private Long idDossier;
    private String reference;
    private LocalDate dateOuverture;
    private String statut;
    private String niveauRisque;
    private LocalDate dateCloture;
    private Double montantInitial;
    private Double montantRecupere;

    private Long clientId;
    private Long chargeDossierId;
}

