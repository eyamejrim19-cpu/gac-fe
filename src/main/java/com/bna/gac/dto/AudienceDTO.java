package com.bna.gac.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AudienceDTO {

    private Long idAudience;
    private LocalDate dateAudience;
    private String typeAudience;
    private String decision;
    private String observation;

    private Long dossierId;
}