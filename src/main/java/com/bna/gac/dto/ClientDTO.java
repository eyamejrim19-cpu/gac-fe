package com.bna.gac.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ClientDTO {

    private Long idClient;
    private String nom;
    private String prenom;
    private String tel;
    private String adresse;
    private String cin;
    private LocalDate dateCreation;
}