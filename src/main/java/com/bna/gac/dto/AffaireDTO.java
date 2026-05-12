package com.bna.gac.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AffaireDTO {

    private Long idAffaire;
    private String numeroProcedure;
    private String statut;
    private String tribunal;
    private String jugement;
    private LocalDate dateDebut;
    private Long dossierId;
    private Long prestataireId;
    private String commentaireRejet;
}