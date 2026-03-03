package com.bna.gac.dto;

import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RisqueDTO {

    private Long id;

    private Double montantPrincipal;

    private Double montantInteret;

    private Double montantTotale;

    private Double tauxInteret;

    private String periode;

    private LocalDate dateContrat;

    private LocalDate dateDebloquage;

    private LocalDate dateEcheance;

    public Long getDossierId() {
        return null;
    }
}
