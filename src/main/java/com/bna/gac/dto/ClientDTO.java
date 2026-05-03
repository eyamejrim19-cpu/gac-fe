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
    private String tel;
    private String email;
    private String adresse;
    private Boolean active;
    private LocalDateTime dateCreation;

    public String getTypeClient() {
        return "";
    }

    public void setTypeClient(String type) {
    }

    public String getRne() {
        return "";
    }
}
