package com.bna.gac.entities;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Responsable extends User {

    private boolean validerDossier;
    private boolean validerFacture;
    private boolean validerMission;
    private boolean validerFactures;
    private boolean consulterStatistic;
}