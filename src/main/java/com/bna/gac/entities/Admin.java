package com.bna.gac.entities;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Admin extends User {

    private boolean gererUtilisateur;
    private boolean gererProfil;

    public void setCreatedAt(LocalDateTime now) {
    }

    public void setEnabled(boolean b) {
    }
}
