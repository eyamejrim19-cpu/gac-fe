package com.bna.gac.repositories;

import com.bna.gac.entities.Huissier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HuissierRepository extends JpaRepository<Huissier, Long> {}
