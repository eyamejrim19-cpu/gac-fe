package com.bna.gac.repositories;

import com.bna.gac.entities.Risque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RisqueRepository extends JpaRepository<Risque, Long> {

    List<Risque> findByDossierIdDossier(Long dossierId);
}