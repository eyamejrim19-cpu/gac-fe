package com.bna.gac.dto;

import lombok.Data;

import java.time.LocalDate;
@Data

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

    // Getters & Setters
}
