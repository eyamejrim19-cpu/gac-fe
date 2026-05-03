package com.bna.gac.repositories;

import com.bna.gac.entities.Avocat;
import com.bna.gac.entities.Expert;
import com.bna.gac.entities.Huissier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpertRepository extends JpaRepository<Expert, Long> {}


