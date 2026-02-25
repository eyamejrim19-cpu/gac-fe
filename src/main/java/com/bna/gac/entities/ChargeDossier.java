package com.bna.gac.entities;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ChargeDossier extends User {

    private boolean gererDossier;
    private boolean gererAffaire;
    private boolean gererMission;
    private boolean gererFactures;
    private boolean gererPrestataires;
}