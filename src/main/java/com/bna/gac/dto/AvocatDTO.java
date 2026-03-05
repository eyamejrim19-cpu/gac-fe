package com.bna.gac.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AvocatDTO {

    private Long id;
    private String username;
    private String email;
    private Boolean enabled;
    private String barreau;
    private String domaine;
}
