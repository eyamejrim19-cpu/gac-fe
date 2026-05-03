package com.bna.gac.repositories;

import com.bna.gac.entities.Audience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AudienceRepository extends JpaRepository<Audience, Long> {
}

