package com.bna.gac.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Audience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAudience;

    private LocalDateTime dateAudience;
    private String decision;
    private String commentaire;

    @ManyToOne
    @JoinColumn(name = "affaire_id")
    private Affaire affaire;

    public void setDossier(DossierContentieux dossier) {

    }
}