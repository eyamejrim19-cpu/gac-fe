package com.bna.gac.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_client")
    private Long id;

    private String nom;
    private String prenom;
    private String cin;
    private String tel;
    private String email;
    private String adresse;

    private Boolean active;

    @Column(name = "date_creation")
    private LocalDateTime dateCreation;
}
