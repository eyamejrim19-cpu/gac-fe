package com.bna.gac.entities;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Avocat extends Prestataire {

    private String barreau;

    public void setCreatedAt(LocalDateTime now) {
    }

    public void setEnabled(boolean b) {
    }

    public void setDomaine(String domaine) {
    }

    public void setUsername(String username) {
    }
}