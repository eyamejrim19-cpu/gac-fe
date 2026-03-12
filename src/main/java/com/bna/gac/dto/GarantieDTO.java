package com.bna.gac.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GarantieDTO {

    private Long idGarantie;
    private String typeGarantie;
    private String description;
    private Double valeur;
    private String statut;
    private Long risqueId;
}