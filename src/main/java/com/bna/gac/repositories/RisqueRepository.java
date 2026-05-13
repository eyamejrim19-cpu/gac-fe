package com.bna.gac.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.bna.gac.entities.Risque;

import java.util.List;

public interface RisqueRepository extends JpaRepository<Risque, Long> {
    List<Risque> findByDossier_IdDossier(Long dossierId);

    @Query("SELECT COALESCE(SUM(r.montantTotal), 0) FROM Risque r WHERE r.dossier.idDossier = :dossierId")
    Double sumMontantTotalByDossierId(@Param("dossierId") Long dossierId);
}
