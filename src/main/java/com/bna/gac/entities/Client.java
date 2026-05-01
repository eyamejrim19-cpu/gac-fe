package com.bna.gac.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "client")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_client")
    private Long id;

    @Column(nullable = false)
    private String nom;

    private String prenom;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String tel;

    private String cin;
    private String rne;
    private String adresse;

    @Column(name = "type_client", nullable = false)
    private String typeClient;

    @Column(nullable = false)
    private Boolean active;

    @Column(name = "date_creation", nullable = false, updatable = false)
    private LocalDateTime dateCreation;

    @PrePersist
    public void prePersist() {
        if (dateCreation == null) dateCreation = LocalDateTime.now();
        if (active == null) active = true;
        if (typeClient == null) typeClient = "PHYSIQUE";
        enforceIdentifierIsolation();
    }

    /**
     * JPA lifecycle hook — fires before every UPDATE.
     * Guarantees the non-applicable identifier is null in the DB row,
     * even if a bug in the service layer bypassed the validation.
     */
    @PreUpdate
    public void preUpdate() {
        enforceIdentifierIsolation();
    }

    /**
     * Clears the identifier that does not belong to this client type.
     *   PHYSIQUE → rne must always be null
     *   MORALE   → cin must always be null
     */
    private void enforceIdentifierIsolation() {
        if ("PHYSIQUE".equalsIgnoreCase(typeClient)) {
            this.rne = null;
        } else if ("MORALE".equalsIgnoreCase(typeClient)) {
            this.cin = null;
        }
    }
}