package com.bna.gac.dto;

import com.bna.gac.entities.TypePrestataire;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrestataireDTO {

    private Long idPrestataire;

    private TypePrestataire typePrestataire;

    private String nom;
    private String prenom;
    private String telephone;
    private String email;
    private String adresse;
    private String specialite;

    private Double tarifJournalier;

    private boolean actif;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
