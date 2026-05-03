package com.bna.gac.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
public class ChargeDossier extends User {

    private String telephone;

    @OneToMany(mappedBy = "chargeDossier")
    private Set<DossierContentieux> dossiers;
}
