package com.bna.gac.repositories;

import com.bna.gac.entities.Mission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MissionRepository extends JpaRepository<Mission, Long> {
    long countByStatut(String statut);
    List<Mission> findByAffaire_IdAffaire(Long affaireId);
}

