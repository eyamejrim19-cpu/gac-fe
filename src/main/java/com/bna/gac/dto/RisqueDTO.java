package com.bna.gac.dto;

import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RisqueDTO {

    private Long id;
    private String reference;
    private Long dossierId;

    private Double montantPrincipal;

    private Double montantInteret;

    private Double montantTotal;

    private Double tauxInteret;

    private Integer periode;

    private LocalDate dateContrat;

    private LocalDate dateDeblocage;

    private LocalDate dateEcheance;
}

