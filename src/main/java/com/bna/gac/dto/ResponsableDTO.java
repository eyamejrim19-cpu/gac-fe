package com.bna.gac.dto;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponsableDTO {

    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
}
