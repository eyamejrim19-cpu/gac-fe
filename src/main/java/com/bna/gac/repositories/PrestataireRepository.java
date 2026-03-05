package com.bna.gac.repositories;


import com.bna.gac.entities.Garantie;
import org.springframework.data.jpa.repository.JpaRepository;
import com.bna.gac.entities.Prestataire;
import org.springframework.stereotype.Repository;

public interface PrestataireRepository extends JpaRepository<Prestataire, Long> {
    @Repository
    interface GarantieRepository extends JpaRepository<Garantie, Long> {
    }
}
