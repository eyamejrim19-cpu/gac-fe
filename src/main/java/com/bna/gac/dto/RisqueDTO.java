package com.bna.gac.dto;

import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RisqueDTO {

    private Long id;
    private Long dossierId;

    private Double montantPrincipal;

    private Double montantInteret;

    private Double montantTotal;

    private Double tauxInteret;

    private String periode;

    private LocalDate dateContrat;

    private LocalDate dateDeblocage;

    private LocalDate dateEcheance;
}

