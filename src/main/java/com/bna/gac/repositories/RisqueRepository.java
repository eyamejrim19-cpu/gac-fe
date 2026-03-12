package com.bna.gac.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bna.gac.entities.Risque;

public interface RisqueRepository extends JpaRepository<Risque, Long> {
}