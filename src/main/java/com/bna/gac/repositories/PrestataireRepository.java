package com.bna.gac.repositories;

import com.bna.gac.entities.Prestataire;
import com.bna.gac.entities.TypePrestataire;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PrestataireRepository extends JpaRepository<Prestataire, Long> {

    List<Prestataire> findByTypePrestataire(TypePrestataire typePrestataire);

    long countByActif(boolean actif);

    /**
     * Paginated search with optional filters.
     *
     * filterByType  — pass true  when type != null, false to skip type filter
     * filterByActif — pass true  when actif != null, false to skip actif filter
     *
     * This avoids the ":param IS NULL" anti-pattern which is unreliable with
     * enum-typed parameters in Hibernate + MySQL.
     */
    @Query("""
        SELECT p FROM Prestataire p
        WHERE (:filterByType  = false OR p.typePrestataire = :type)
          AND (:filterByActif = false OR p.actif = :actif)
          AND (:search IS NULL
               OR LOWER(p.nom)        LIKE LOWER(CONCAT('%', :search, '%'))
               OR LOWER(p.prenom)     LIKE LOWER(CONCAT('%', :search, '%'))
               OR LOWER(p.email)      LIKE LOWER(CONCAT('%', :search, '%'))
               OR LOWER(p.specialite) LIKE LOWER(CONCAT('%', :search, '%')))
        """)
    Page<Prestataire> findPaginated(
            @Param("filterByType")  boolean filterByType,
            @Param("type")          TypePrestataire type,
            @Param("filterByActif") boolean filterByActif,
            @Param("actif")         boolean actif,
            @Param("search")        String search,
            Pageable pageable
    );
}
