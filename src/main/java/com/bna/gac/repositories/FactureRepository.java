package com.bna.gac.repositories;

import com.bna.gac.entities.Facture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FactureRepository extends JpaRepository<Facture, Long> {

    // exemple: search by numero
    Facture findByNumero(String numero);

    // filter by statut
    List<Facture> findByStatut(String statut);

    // filter by type
    List<Facture> findByTypeFacture(String typeFacture);
}
