package com.bna.gac.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrestataireDTO {

    private Long id;
    private String nom;
    private String prenom;
    private String type;
    private String email;
    private String telephone;
    private String adresse;
    private String specialite;
    private Double tarifJournalier;
    private boolean actif;
    private String rib;
}

