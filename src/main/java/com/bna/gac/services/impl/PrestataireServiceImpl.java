package com.bna.gac.services.impl;

import com.bna.gac.entities.Prestataire;
import com.bna.gac.entities.TypePrestataire;
import com.bna.gac.repositories.PrestataireRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PrestataireServiceImpl implements PrestataireService {

    private final PrestataireRepository prestataireRepository;

    @Override
    public Prestataire addPrestataire(Prestataire prestataire) {
        return prestataireRepository.save(prestataire);
    }

    @Override
    public List<Prestataire> getAllPrestataires() {
        return prestataireRepository.findAll();
    }

    @Override
    public Prestataire getPrestataireById(Long id) {
        return prestataireRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prestataire non trouvé"));
    }

    @Override
    public List<Prestataire> getPrestatairesByType(TypePrestataire type) {
        return prestataireRepository.findByTypePrestataire(type);
    }

    @Override
    public Prestataire updatePrestataire(Long id, Prestataire prestataire) {

        Prestataire existing = getPrestataireById(id);

        existing.setNom(prestataire.getNom());
        existing.setPrenom(prestataire.getPrenom());
        existing.setTelephone(prestataire.getTelephone());
        existing.setEmail(prestataire.getEmail());
        existing.setSpecialite(prestataire.getSpecialite());
        existing.setTarifJournalier(prestataire.getTarifJournalier());
        existing.setActif(prestataire.getActif());
        existing.setTypePrestataire(prestataire.getTypePrestataire());

        return prestataireRepository.save(existing);
    }

    @Override
    public void deletePrestataire(Long id) {
        prestataireRepository.deleteById(id);
    }

    public Prestataire updateStatus(Long id, boolean actif) {

        Prestataire p = prestataireRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prestataire non trouvé"));

        p.setActif(actif);

        return prestataireRepository.save(p);
    }
}
