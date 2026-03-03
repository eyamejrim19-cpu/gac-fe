package com.bna.gac.entities;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Huissier extends Prestataire {

    private String zoneIntervention;

    public void setCreatedAt(LocalDateTime now) {
    }

    public void setEnabled(boolean b) {
    }

    public void setCabinet(String cabinet) {
    }

    public void setUsername(String username) {
    }
}
