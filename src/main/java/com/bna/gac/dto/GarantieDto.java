package com.bna.gac.dto;

import lombok.Data;

import java.util.List;

@Data
public class GarantieDto {

    private Long idGarantie;
    private String typeGarantie;
    private String description;
    private Double valeur;
    private String statut;

    private List<Long> risqueIds;
}
