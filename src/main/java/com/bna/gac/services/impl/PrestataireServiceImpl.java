package com.bna.gac.services.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.bna.gac.repositories.PrestataireRepository;
import com.bna.gac.mapper.PrestataireMapper;
import com.bna.gac.dto.PrestataireDTO;
import com.bna.gac.entities.Prestataire;
import com.bna.gac.services.impl.PrestataireService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PrestataireServiceImpl implements PrestataireService {

    private final PrestataireRepository repository;
    private  PrestataireMapper mapper;

    public PrestataireServiceImpl(PrestataireRepository repository) {
        this.repository = repository;
    }

    @Override
    public PrestataireDTO save(PrestataireDTO dto) {
        Prestataire entity = mapper.toEntity(dto);
        return mapper.toDTO(repository.save(entity));
    }

    @Override
    public List<PrestataireDTO> findAll() {
        return mapper.toDTOList(repository.findAll());
    }

    @Override
    public PrestataireDTO findById(Long id) {
        Prestataire entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prestataire not found"));
        return mapper.toDTO(entity);
    }

    @Override
    public PrestataireDTO update(Long id, PrestataireDTO dto) {

        Prestataire existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prestataire not found"));

        existing.setNom(dto.getNom());
        existing.setType(dto.getType());
        existing.setEmail(dto.getEmail());
        existing.setTelephone(dto.getTelephone());
        existing.setAdresse(dto.getAdresse());

        return mapper.toDTO(repository.save(existing));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Prestataire addPrestataire(Prestataire prestataire) {
        repository.save(prestataire);
        return prestataire;
    }

    public List<Prestataire> getAllPrestataires() {
        return repository.findAll();
    }

    public Prestataire getPrestataireById(Long id) {
        return null;
    }

    public Prestataire updatePrestataire(Long id, Prestataire prestataire) {

        Prestataire existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prestataire not found"));

        existing.setNom(prestataire.getNom());
        existing.setType(prestataire.getTypePrestataire());
        existing.setEmail(prestataire.getEmail());
        existing.setTelephone(prestataire.getTelephone());
        existing.setAdresse(prestataire.getAdresse());
        existing.setSpecialite(prestataire.getSpecialite());
        existing.setTarifJournalier(prestataire.getTarifJournalier());
        existing.setActif(prestataire.isActif());
        repository.save(existing);
        return existing;
    }

    public void deletePrestataire(Long id) {
        repository.deleteById(id);
    }
}
