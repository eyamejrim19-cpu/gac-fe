package com.bna.gac.repositories;

import com.bna.gac.entities.ChargeDossier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChargeDossierRepository extends JpaRepository<ChargeDossier, Long> {
}

