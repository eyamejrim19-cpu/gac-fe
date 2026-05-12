package com.bna.gac.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MissionDTO {

    private Long idMission;
    private String typeMission;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String statut;
    private String resultat;
    private String commentaire;

    private Long prestataireId;
    private Long affaireId;
    private String commentaireRejet;
}
