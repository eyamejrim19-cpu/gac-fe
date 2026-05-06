package com.bna.gac.dto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ClientDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String cin;
    private String rne;
    private String tel;
    private String email;
    private String adresse;
    private String typeClient;
    private Boolean active;
    private LocalDateTime dateCreation;
}
