package com.bna.gac.repositories;

import com.bna.gac.entities.Prestataire;
import com.bna.gac.entities.TypePrestataire;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrestataireRepository extends JpaRepository<Prestataire, Long> {

    List<Prestataire> findByTypePrestataire(TypePrestataire type);

}
