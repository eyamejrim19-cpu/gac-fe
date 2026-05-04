package com.bna.gac.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bna.gac.entities.Risque;

import java.util.List;

public interface RisqueRepository extends JpaRepository<Risque, Long> {
    List<Risque> findByDossier_IdDossier(Long dossierId);
}
