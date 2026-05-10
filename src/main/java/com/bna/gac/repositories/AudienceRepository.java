package com.bna.gac.repositories;

import com.bna.gac.entities.Audience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AudienceRepository extends JpaRepository<Audience, Long> {
    List<Audience> findByAffaire_IdAffaire(Long affaireId);
}

