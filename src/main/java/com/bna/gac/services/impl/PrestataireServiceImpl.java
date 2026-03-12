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
@RequiredArgsConstructor
public class PrestataireServiceImpl implements PrestataireService {

    private final PrestataireRepository repository;
    private final PrestataireMapper mapper;

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
        return prestataire;
    }

    public List<Prestataire> getAllPrestataires() {
        return List.of();
    }

    public Prestataire getPrestataireById(Long id) {
        return null;
    }

    public Prestataire updatePrestataire(Long id, Prestataire prestataire) {
        return prestataire;
    }

    public void deletePrestataire(Long id) {
    }
}
