package com.bna.gac.entities;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Expert extends Prestataire {

    private String domaineExpertise;

    public void setCreatedAt(LocalDateTime now) {
    }

    public void setEnabled(boolean b) {
    }
}