package com.bna.gac.repositories;

import com.bna.gac.entities.Affaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AffaireRepository extends JpaRepository<Affaire, Long> {
}
