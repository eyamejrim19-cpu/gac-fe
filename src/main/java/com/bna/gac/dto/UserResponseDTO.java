package com.bna.gac.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserResponseDTO {

    private Long id;
    private String username;
    private String email;
    private String nom;
    private String prenom;
    private List<String> roles;
}