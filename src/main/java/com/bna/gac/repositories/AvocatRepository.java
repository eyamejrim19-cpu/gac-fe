package com.bna.gac.repositories;

import com.bna.gac.entities.Avocat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvocatRepository extends JpaRepository<Avocat, Long> {}

