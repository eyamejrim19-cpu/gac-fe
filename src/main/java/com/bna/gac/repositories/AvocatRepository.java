package com.bna.gac.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvocatRepository<Avocat> extends JpaRepository<Avocat, Long> {}
