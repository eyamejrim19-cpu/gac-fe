package com.bna.gac.entities;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Avocat extends Prestataire {

    private String barreau;
}

