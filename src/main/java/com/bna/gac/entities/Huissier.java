package com.bna.gac.entities;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Huissier extends Prestataire {

    private String zoneIntervention;
}

