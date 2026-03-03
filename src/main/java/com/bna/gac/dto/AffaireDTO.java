package com.bna.gac.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AffaireDTO {

    private Long idAffaire;
    private String reference;
    private String typeAffaire;
    private String statut;
    private LocalDate dateCreation;

    private Long clientId;
}