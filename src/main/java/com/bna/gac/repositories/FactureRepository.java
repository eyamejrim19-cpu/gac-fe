package com.bna.gac.repositories;

import com.bna.gac.entities.Facture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FactureRepository extends JpaRepository<Facture, Long> {
    Facture findByNumero(String numero);
    List<Facture> findByStatut(String statut);
    List<Facture> findByTypeFacture(String typeFacture);
    long countByStatut(String statut);
}

