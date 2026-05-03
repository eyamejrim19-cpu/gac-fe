package com.bna.gac.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChargeDossierDTO {

    private Long id;
    private String username;
    private String email;
    private String telephone;
    private Boolean enabled;
}

