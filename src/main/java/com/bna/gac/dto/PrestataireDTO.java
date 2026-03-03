package com.bna.gac.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrestataireDTO {

    private Long id;
    private String nom;
    private String type;
    private String email;
    private String telephone;
    private String adresse;
}