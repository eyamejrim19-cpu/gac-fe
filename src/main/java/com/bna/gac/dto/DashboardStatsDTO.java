package com.bna.gac.dto;

import lombok.Data;

@Data
public class DashboardStatsDTO {

    private long totalDossiers;
    private long dossiersActifs;
    private long dossiersClotures;

    private double montantTotalRecupere;
    private double montantTotalImpaye;

    private double tauxRecouvrement;

    private long missionsEnCours;
    private long missionsTerminees;

    private long facturesEnAttente;
    private long facturesPayees;

    private long prestatairesActifs;
    private long clientsActifs;
}
