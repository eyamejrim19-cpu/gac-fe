package com.bna.gac.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class ChargeDossier extends User {

    private String telephone;

    @OneToMany(mappedBy = "chargeDossier")
    private List<DossierContentieux> dossiers;

    public void setCreatedAt(LocalDateTime now) {
    }

    public void setEnabled(boolean b) {

    }
}