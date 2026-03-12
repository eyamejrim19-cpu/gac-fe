package com.bna.gac.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DossierContentieuxDTO {

    private Long idDossier;
    private String reference;
    private String statut;
    private String niveauRisque;
    private Double montantInitial;
    private Double montantRecupere;

    private Long clientId;
}