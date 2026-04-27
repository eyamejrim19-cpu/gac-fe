package com.bna.gac.repositories;

import com.bna.gac.entities.DossierContentieux;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DossierContentieuxRepository extends JpaRepository<DossierContentieux, Long> {

    long countByStatut(String statut);

    List<DossierContentieux> findTop5ByOrderByIdDossierDesc();
}