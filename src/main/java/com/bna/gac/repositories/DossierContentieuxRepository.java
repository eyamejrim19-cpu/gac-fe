package com.bna.gac.repositories;

import com.bna.gac.entities.DossierContentieux;
import com.bna.gac.entities.enums.DossierStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DossierContentieuxRepository extends JpaRepository<DossierContentieux, Long> {

    long countByStatut(DossierStatus statut);

    List<DossierContentieux> findTop5ByOrderByIdDossierDesc();

    List<DossierContentieux> findByOrderByIdDossierDesc(Pageable pageable);

    @Query("SELECT SUM(d.montantRecupere) FROM DossierContentieux d")
    Double sumMontantRecupere();

    @Query("SELECT SUM(d.montantInitial - d.montantRecupere) FROM DossierContentieux d")
    Double sumMontantImpaye();

    @Query("SELECT SUM(d.montantInitial) FROM DossierContentieux d")
    Double sumMontantInitial();
}
