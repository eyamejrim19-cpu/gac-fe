package com.bna.gac.services.impl;

import com.bna.gac.entities.Prestataire;
import com.bna.gac.entities.TypePrestataire;

import java.util.List;

public interface PrestataireService {

    Prestataire addPrestataire(Prestataire prestataire);

    List<Prestataire> getAllPrestataires();

    Prestataire getPrestataireById(Long id);

    List<Prestataire> getPrestatairesByType(TypePrestataire type);

    Prestataire updatePrestataire(Long id, Prestataire prestataire);

    void deletePrestataire(Long id);

}