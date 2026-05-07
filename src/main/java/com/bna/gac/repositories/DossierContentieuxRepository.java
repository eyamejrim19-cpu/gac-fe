package com.bna.gac.repositories;

import com.bna.gac.entities.DossierContentieux;
import com.bna.gac.entities.enums.DossierStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    // Returns [month_number, total_recovered] for the last N months
    @Query(value = """
        SELECT MONTH(d.date_ouverture) AS mois, COALESCE(SUM(d.montant_recupere), 0) AS total
        FROM dossier_contentieux d
        WHERE d.date_ouverture >= DATE_SUB(CURDATE(), INTERVAL :months MONTH)
        GROUP BY MONTH(d.date_ouverture)
        ORDER BY MONTH(d.date_ouverture)
        """, nativeQuery = true)
    List<Object[]> findMonthlyRecovery(@Param("months") int months);
}
