package com.bna.gac.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "prestataire")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Prestataire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPrestataire;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypePrestataire typePrestataire;

    @Column(nullable = false)
    private String nom;

    private String prenom;
    private String telephone;

    @Column(unique = true)
    private String email;

    private String adresse;
    private String specialite;

    private Double tarifJournalier;

    @Builder.Default
    @Column(nullable = false)
    private boolean actif = true;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
