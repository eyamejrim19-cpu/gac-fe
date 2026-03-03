package com.bna.gac.repositories;

import com.bna.gac.entities.DossierContentieux;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DossierContentieuxRepository
        extends JpaRepository<DossierContentieux, Long> {

    Optional<DossierContentieux> findByReference(String reference);

    List<DossierContentieux> findByStatut(String statut);
}