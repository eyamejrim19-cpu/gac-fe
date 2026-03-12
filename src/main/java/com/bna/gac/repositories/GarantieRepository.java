package com.bna.gac.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bna.gac.entities.Garantie;

public interface GarantieRepository extends JpaRepository<Garantie, Long> {
}