package com.bna.gac.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class ChargeDossier extends User {

    private String telephone;

    @OneToMany(mappedBy = "chargeDossier")
    private Set<DossierContentieux> dossiers;

    public void setCreatedAt(LocalDateTime now) {
    }

    public void setEnabled(boolean b) {

    }
}