package com.bna.gac.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bna.gac.entities.DossierContentieux;

public interface DossierContentieuxRepository extends JpaRepository<DossierContentieux, Long> {
}